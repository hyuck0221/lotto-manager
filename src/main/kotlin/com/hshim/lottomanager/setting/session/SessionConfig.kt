package com.hshim.lottomanager.setting.session

import org.springframework.context.annotation.Configuration
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession

@Configuration
@EnableJdbcHttpSession
class SessionConfig {
}