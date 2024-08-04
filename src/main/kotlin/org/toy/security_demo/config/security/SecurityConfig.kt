package org.toy.security_demo.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

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

    @Bean(name = ["BaseSecurityConfig"])
    fun sessionFilterChain(http: HttpSecurity): SecurityFilterChain{

        /*
        * 세션 방식 로그인을 구현 하는 FilterChain
        * */

        http
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .logout { it.disable() }
            .csrf {
            }

        return http.build()
    }

}