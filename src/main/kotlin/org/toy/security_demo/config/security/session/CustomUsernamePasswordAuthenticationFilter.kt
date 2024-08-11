package org.toy.security_demo.config.security.session

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.authentication.AuthenticationFailureHandler

class CustomUsernamePasswordAuthenticationFilter(
    defaultFilterProcessesUrl: String,
    authenticationManager: AuthenticationManager,
): AbstractAuthenticationProcessingFilter(defaultFilterProcessesUrl, authenticationManager) {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        TODO("Username 과 Password 를 통해 Authentication 과정 작성 필요")
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        // ContextHolder 에서 Empty Context 생성후 Authentication 객체를 넣어준다.
        super.successfulAuthentication(request, response, chain, authResult)
    }
}

