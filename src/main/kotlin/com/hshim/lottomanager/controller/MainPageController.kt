package com.hshim.lottomanager.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginController {
    @GetMapping("/login")
    fun login() = "login.html"

    @GetMapping("/main")
    fun main() = "index.html"
}