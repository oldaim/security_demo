package org.toy.security_demo.config.security

import org.springframework.boot.ssl.DefaultSslBundleRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.toy.security_demo.SecurityDemoApplication

@EnableWebSecurity
@Configuration
class SecurityConfig {

    /*
    *  Security 설정시 httpBasic 을 사용하게되면 기본적으로 설정된 SpringSecurity 의 흐름을 따라가게 되는것으로 보인다.
    *  따라서 Custom 한 인증방식을 사용하고 싶다면 disable 을 하는게 좋아보인다.
    *
    *  formLogin 과 logout 의 경우 disable 을 하지 않으면 AutoConfiguration 에서 강제로 LoginForm 페이지로 리다이랙트 하는것을 예전에 경험한적이 있다.
    *  계속 그런 현상이 발생할경우 Log 를 Trace 로 변경하여 SecurityFilterChain 이 어떤 필터들을 적용해 인증을 거치는지 확인 할 수 있다.
    * */

    companion object{
        private const val SESSION_BASE_URI = "/api/session/**" // Session 방식 테스트를 위한 BASE_URI
        private const val INVALID_SESSION_URI = "/timeout"
        //Session Registry Bean Name
    }

    @Bean(name = ["SessionSecurityConfig"])
    fun sessionFilterChain(http: HttpSecurity): SecurityFilterChain{

        /*
        * 세션 방식 로그인을 구현 하는 FilterChain
        * */

        http
            .securityContext {
                it.securityContextRepository(HttpSessionSecurityContextRepository())
                /*
                    해당 Repository 에 SecurityContext 정보가 있다면 해당 정보를 가져오고, 저장되어 있지 않다면 새로운 SecurityContext 를 생성

                    5.7 이상부터 기본으로 사용되는 SecurityContextHolderFilter 에서는 더이상 securityRepository 에 자동으로 저장해주지 않고 불러오는 역할 만 진행
                    즉 직접 SecurityContext 정보를 저장해야 함.

                    Spring Security 5에서는 기본적으로 `SessionManagementFilter`를 사용하여 사용자가 방금 인증되었는지 감지하고 `SessionAuthenticationStrategy`를 호출합니다.

                    이 설정의 문제점은 일반적인 설정에서 매 요청마다 `HttpSession`을 읽어야 한다는 것입니다.

                    Spring Security 6에서는 기본적으로 인증 메커니즘 자체가 `SessionAuthenticationStrategy`를 호출해야 합니다.

                    이는 인증이 완료되는 시점을 감지할 필요가 없음을 의미하며, 따라서 매 요청마다 `HttpSession`을 읽을 필요가 없습니다.
                 */
            }
            .securityMatcher(SESSION_BASE_URI) //해당 URI 에 대해서는 이 SecurityFilterChain 을 사용하도록 지정
            .authorizeHttpRequests {
                it.requestMatchers(SESSION_BASE_URI).permitAll()
            }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .logout { it.disable() }
            .csrf {
                // 추후 구현
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 인증된 사용자에게만 Session 을 생성하도록 지정
                it.sessionConcurrency {
                    // 해당 설정을 통해 한 유저당 세션은 1개로 제한하고 두번째 로그인시 첫번째 세션을 무효화 하도록 설정 할수 있다.
                        concurrency ->
                    concurrency.maximumSessions(1)
                    concurrency.maxSessionsPreventsLogin(true)
                }
                it.invalidSessionUrl(INVALID_SESSION_URI) // 세션 타임 아웃이 된 요청을 Redirect 하는 과정
                // it.invalidSessionStrategy() 타임 아웃이 된 세션을 어떻게 처리할지 에 대한 전략 default 설정은 SimpleRedirectInvalidSessionStrategy
            }



        return http.build()
    }


}