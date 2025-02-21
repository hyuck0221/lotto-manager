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

    @GetMapping("/speedo/500")
    fun speedo500() = "page/game/speedo/500"

    @GetMapping("/speedo/1000")
    fun speedo1000() = "page/game/speedo/1000"

    @GetMapping("/speedo/2000")
    fun speedo2000() = "page/game/speedo/2000"
}