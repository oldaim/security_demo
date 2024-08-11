## Authentication Architecture

- SecurityContextHolder
- SecurityContext
- Authentication
- GrantedAuthority
- AuthenticationManager
- ProviderManager
- AuthenticationProvider
- Request Credentials With AuthenticationEntryPoint
- AbstractAuthenticationProcessingFilter

---
### SecurityContextHolder

SecurityContextHolder 는 누군가의 인증 정보를 저장하는 역할을 한다.
SpringSecurity 는 어떻게 값이 채워지는지에 대해서는 신경쓰지 않는다. 만약 이 안에 값이 들어 있다면, 그 값을 현재 인증된 사용자로 간주한다.

```kotlin
    val context: SecurityContext = SecurityContextHolder.createEmptyContext() 
    val authentication: Authentication = TestingAuthenticationToken("username", "password", "ROLE_USER") 
    context.authentication = authentication
    SecurityContextHolder.setContext(context)
```
SecurityContext 는 빈 상태로 생성 되어야 한다. 

```
    SecurityContextHolder.getContext().setAuthentication(authentication) // 이렇게 하면 안된다.
    
```
왜냐하면 멀티 스레드 환경에서 경쟁상태를 피하기 위함이다.

SecurityContextHolder 는 ThreadLocal 을 사용하여 현재 스레드에 대한 정보를 저장한다. 
즉 이말은 동일한 스레드에만 이용 가능 하다는 것이다. 

### SecurityContext

SecurityContext 는 인증 정보를 저장하는 역할을 한다.

### Authentication

Authentication은 특이하게 두가지 역할을 하게 되는데 Authentication 객체는 AuthenticationManger 에서 인증이 필요한 사용자의 정보 일 수도 있고
인증이 완료된 사용자 정보 일수 있다.

isAuthenticated() 메소드를 통해 인증이 완료되었는지 확인 할 수 있다.

### ProviderManager
AuthenticationManager 의 가장 흔한 구현체로서 ProviderManager 는 여러개의 AuthenticationProvider 를 가질수 있다.
AuthenticationProvider 에서 인증을 처리하고 그다음으로 인증을 넘기는 방식으로 여러 인증을 한꺼번에 처리 할수 있는 기능을 제공한다.
ProviderManager는 인증 요청이 성공한후 반환된 Authentication 객체에서 민감한 자격증명 정보를 삭제하려고 시도한다.
그런데 이는 무상태 어플리케이션에서 인증을 캐싱하는 경우 문제가 발생 할수도 있다.
음 이런 시나리오는 생각해본적이 없는데..