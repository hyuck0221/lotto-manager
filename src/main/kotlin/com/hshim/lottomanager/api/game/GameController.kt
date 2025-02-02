package com.hshim.lottomanager.api.game

import com.hshim.lottomanager.service.game.GameQueryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/game")
class GameController(
    private val gameQueryService: GameQueryService,
) {
    @GetMapping("/list")
    fun games(
        @RequestParam(required = false) search: String?
    ) = gameQueryService.games(search)
}