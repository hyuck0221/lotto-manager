package com.hshim.lottomanager.service.game.point

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.game.repository.PointRepository
import com.hshim.lottomanager.model.game.point.PointResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PointQueryService(private val pointRepository: PointRepository) {
    fun getPoint(user: User): PointResponse {
        val point = pointRepository.findByUser(user)
        return PointResponse(point)
    }
}