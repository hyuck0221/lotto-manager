package com.hshim.lottomanager.controller.page.game

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/page/game")
class GamePageController {
    @GetMapping("/main")
    fun main() = "page/game/main"

    @GetMapping("/lotto")
    fun lotto() = "page/game/lotto"
}