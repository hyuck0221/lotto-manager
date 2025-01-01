package com.hshim.lottomanager.controller.page.account.user

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/page/account/user")
class UserPageController {
    @GetMapping("/my-info")
    fun myInfo() = "page/account/user/my-info"
}