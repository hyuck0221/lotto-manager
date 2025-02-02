package com.hshim.lottomanager.service.game.dailycheck

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.game.DailyCheck
import com.hshim.lottomanager.database.game.repository.DailyCheckRepository
import com.hshim.lottomanager.exception.GlobalException
import com.hshim.lottomanager.service.game.point.PointCommandService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DailyCheckCommandService(
    private val dailyCheckRepository: DailyCheckRepository,
    private val dailyCheckQueryService: DailyCheckQueryService,
    private val pointCommandService: PointCommandService,
) {
    fun check(user: User) {
        val dailyCheckResponse = dailyCheckQueryService.getDailyCheck(user)
        if (dailyCheckResponse.todayCheck) throw GlobalException.ALREADY_DAILY_CHECK.exception

        pointCommandService.addPoint(user, dailyCheckResponse.todayPoint)
        dailyCheckRepository.save(DailyCheck(user = user, amount = dailyCheckResponse.todayPoint))
    }
}