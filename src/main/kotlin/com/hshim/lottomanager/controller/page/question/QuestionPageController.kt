package com.hshim.lottomanager.controller.page.question

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/page/question")
class QuestionPageController {
    @GetMapping("/list")
    fun list() = "page/question/list"

    @GetMapping("/detail")
    fun detail() = "page/question/detail"

    @GetMapping("/register")
    fun register() = "page/question/register"

    @GetMapping("/edit")
    fun edit() = "page/question/edit"

    @GetMapping("/reply")
    fun reply() = "page/question/reply"
}