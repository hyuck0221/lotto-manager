package com.hshim.lottomanager.service.game.dailycheck

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.game.repository.DailyCheckRepository
import com.hshim.lottomanager.model.game.dailycheck.DailyCheckResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class DailyCheckQueryService(private val dailyCheckRepository: DailyCheckRepository) {

    fun getDailyCheck(user: User): DailyCheckResponse {
        val now = LocalDate.now()
        val latestCheck = dailyCheckRepository.findTopByUserOrderByCreateDateDesc(user)
        val total = dailyCheckRepository.countAllByUser(user)

        val todayPoint = when {
            latestCheck?.createDate?.toLocalDate() == now -> 0
            latestCheck?.createDate?.toLocalDate() == now.minusDays(1) -> 2000
            else -> 1000
        }

        return DailyCheckResponse(
            todayCheck = latestCheck?.createDate?.toLocalDate() == now,
            todayPoint = todayPoint,
            total = total.toInt(),
        )
    }
}