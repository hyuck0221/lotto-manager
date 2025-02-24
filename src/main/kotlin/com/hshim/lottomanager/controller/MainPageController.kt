package com.hshim.lottomanager.controller

import com.hshim.lottomanager.annotation.PublicEndpoint
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginController {
    @GetMapping("/login")
    @PublicEndpoint
    fun login() = "login.html"

    @GetMapping("/main")
    fun main() = "index.html"
}