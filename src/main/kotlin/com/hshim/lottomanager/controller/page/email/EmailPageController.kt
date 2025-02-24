package com.hshim.lottomanager.controller.page.email

import com.hshim.lottomanager.annotation.PublicEndpoint
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/page/email")
class EmailPageController {
    @GetMapping("/remove-code")
    @PublicEndpoint
    fun removeCode() = "page/email/remove-code"
}