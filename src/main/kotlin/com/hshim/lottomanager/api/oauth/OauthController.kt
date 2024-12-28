package com.hshim.lottomanager.api.oauth

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account/auth")
class OauthController {

    @GetMapping("/check-session")
    fun checkSession(authentication: Authentication?): ResponseEntity<String> {
        return when (authentication != null && authentication.isAuthenticated) {
            true -> ResponseEntity.ok("authenticated")
            false -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthenticated")
        }
    }
}