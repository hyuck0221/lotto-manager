package com.hshim.lottomanager.setting.session

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.session.web.http.CookieSerializer
import org.springframework.session.web.http.DefaultCookieSerializer

@Configuration
class SessionConfig {

    @Bean
    fun cookieSerializer(): CookieSerializer {
        val serializer = DefaultCookieSerializer()
        serializer.setCookieName("JSESSIONID")
        serializer.setCookieMaxAge(30 * 24 * 60 * 60)
        return serializer
    }
}