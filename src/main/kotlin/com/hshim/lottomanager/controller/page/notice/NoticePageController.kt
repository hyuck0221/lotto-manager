package com.hshim.lottomanager.controller.page.notice

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/page/notice")
class NoticePageController {
    @GetMapping("/list")
    fun list() = "page/notice/list"

    @GetMapping("/detail")
    fun detail() = "page/notice/detail"
}