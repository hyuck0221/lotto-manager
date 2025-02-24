package com.hshim.lottomanager.setting.security

import com.hshim.lottomanager.annotation.PublicEndpoint
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Value("\${spring.security.enabled:true}")
    private val securityEnabled: Boolean,
    private val requestMappingHandlerMapping: RequestMappingHandlerMapping,
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val publicUrls = getPublicUrls()
        if (!securityEnabled) {
            http.authorizeHttpRequests { it.anyRequest().permitAll() }
                .csrf { it.disable() }
            return http.build()
        }

        http
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers(
                        "/",
                        "/error",
                        "/icon/**",
                    ).permitAll()
                    .requestMatchers(*publicUrls.toTypedArray()).permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2Login { oauth2Login ->
                oauth2Login
                    .loginPage("/login")
                    .successHandler(CustomAuthenticationSuccessHandler())
            }
            .httpBasic { httpBasic ->
                httpBasic.authenticationEntryPoint { _, response, _ ->
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                }
            }
            .sessionManagement { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            }
            .csrf { it.disable() }
            .logout { logout ->
                logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
            }
        return http.build()
    }

    private fun getPublicUrls(): List<String> {
        val publicUrls = mutableListOf<String>()
        requestMappingHandlerMapping.handlerMethods.forEach { (mapping, handlerMethod) ->
            if (handlerMethod.beanType.isAnnotationPresent(PublicEndpoint::class.java) ||
                handlerMethod.method.isAnnotationPresent(PublicEndpoint::class.java)
            ) publicUrls.addAll(mapping.patternValues)
        }
        return publicUrls
    }
}