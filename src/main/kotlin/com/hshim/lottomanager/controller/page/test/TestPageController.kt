package com.hshim.lottomanager.controller.page.test

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/page/test")
class TestPageController {
    @GetMapping("/qr")
    fun myInfo() = "page/test/qr"
}