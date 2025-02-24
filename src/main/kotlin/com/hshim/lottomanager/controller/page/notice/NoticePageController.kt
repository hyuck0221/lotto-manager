package com.hshim.lottomanager.controller.page.notice

import com.hshim.lottomanager.annotation.PublicEndpoint
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/page/notice")
class NoticePageController {
    @PublicEndpoint
    @GetMapping("/list")
    fun list() = "page/notice/list"

    @GetMapping("/detail")
    @PublicEndpoint
    fun detail() = "page/notice/detail"
}