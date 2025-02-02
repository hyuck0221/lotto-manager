package com.hshim.lottomanager.api.game.point

import com.hshim.lottomanager.model.game.point.PointResponse
import com.hshim.lottomanager.service.account.user.UserQueryService
import com.hshim.lottomanager.service.game.point.PointCommandService
import com.hshim.lottomanager.service.game.point.PointQueryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/game/point")
class PointController(
    private val pointCommandService: PointCommandService,
    private val pointQueryService: PointQueryService,
    private val userQueryService: UserQueryService,
) {
    @GetMapping
    fun getPoint(): PointResponse {
        val user = userQueryService.getUser()
        return pointQueryService.getPoint(user)
    }

    @PostMapping
    fun addPoint(@RequestParam amount: Int) {
        val user = userQueryService.getUser()
        pointCommandService.addPoint(user, amount)
    }

    @DeleteMapping
    fun minusPoint(@RequestParam amount: Int) {
        val user = userQueryService.getUser()
        pointCommandService.minusPoint(user, amount)
    }
}