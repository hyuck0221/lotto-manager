package com.hshim.lottomanager.setting.socket

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig(private val sessionManager: SessionManager) {
    @Bean
    fun customWebSocketHandler(): CustomWebSocketHandler {
        return CustomWebSocketHandler(sessionManager)
    }
}