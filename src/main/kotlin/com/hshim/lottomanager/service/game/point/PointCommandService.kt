package com.hshim.lottomanager.service.game.point

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.game.Point
import com.hshim.lottomanager.database.game.PointLog
import com.hshim.lottomanager.database.game.repository.PointLogRepository
import com.hshim.lottomanager.database.game.repository.PointRepository
import com.hshim.lottomanager.exception.GlobalException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PointCommandService(
    private val pointRepository: PointRepository,
    private val pointLogRepository: PointLogRepository,
) {
    fun addPoint(user: User, amount: Int) {
        val point = pointRepository.findByUserId(user.id)
            ?.apply { this.amount += amount }
            ?: pointRepository.save(Point(user = user, amount = amount))
        pointLogRepository.save(PointLog(point = point, changedAmount = amount, currentAmount = point.amount))
    }

    fun minusPoint(user: User, amount: Int) {
        val point = pointRepository.findByUserId(user.id)
            ?.apply {
                this.amount -= amount
                if (this.amount < 0) throw GlobalException.POINT_UNDER_ZERO.exception
            }
            ?: pointRepository.save(Point(user = user, amount = -amount))
        pointLogRepository.save(PointLog(point = point, changedAmount = -amount, currentAmount = point.amount))
    }
}