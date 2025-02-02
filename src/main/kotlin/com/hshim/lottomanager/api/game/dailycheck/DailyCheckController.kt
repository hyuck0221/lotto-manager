package com.hshim.lottomanager.api.game.dailycheck

import com.hshim.lottomanager.model.game.dailycheck.DailyCheckResponse
import com.hshim.lottomanager.service.account.user.UserQueryService
import com.hshim.lottomanager.service.game.dailycheck.DailyCheckCommandService
import com.hshim.lottomanager.service.game.dailycheck.DailyCheckQueryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/game/daily-check")
class DailyCheckController(
    private val dailyCheckQueryService: DailyCheckQueryService,
    private val dailyCheckCommandService: DailyCheckCommandService,
    private val userQueryService: UserQueryService,
) {
    @GetMapping
    fun getDailyCheck(): DailyCheckResponse {
        val user = userQueryService.getUser()
        return dailyCheckQueryService.getDailyCheck(user)
    }

    @PostMapping
    fun check() {
        val user = userQueryService.getUser()
        dailyCheckCommandService.check(user)
    }
}