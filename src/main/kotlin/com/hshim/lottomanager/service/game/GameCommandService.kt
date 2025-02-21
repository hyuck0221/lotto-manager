package com.hshim.lottomanager.service.game

import com.hshim.lottomanager.database.game.GameLog
import com.hshim.lottomanager.database.game.repository.GameLogRepository
import com.hshim.lottomanager.model.game.Speedo500Response
import com.hshim.lottomanager.service.account.user.UserQueryService
import com.hshim.lottomanager.service.game.point.PointCommandService
import io.autocrypt.sakarinblue.universe.util.ClassUtil.classToMap
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@Service
@Transactional
class GameCommandService(
    private val userQueryService: UserQueryService,
    private val pointCommandService: PointCommandService,
    private val gameLogRepository: GameLogRepository,
) {
    fun speedo500(): Speedo500Response {

        val rankPoint = mapOf(
            0 to 0,
            1 to 200000000,
            2 to 1000000,
            3 to 5000,
            4 to 500,
        )

        val rankPointKr = mapOf(
            1 to "이억원",
            2 to "일백만원",
            3 to "오천원",
            4 to "오백원",
        )

        val user = userQueryService.getUser()
        pointCommandService.minusPoint(user, 500)
        val rank = when {
            Random.nextInt(4000000) == Random.nextInt(4000000) -> 1
            Random.nextInt(200000) == Random.nextInt(200000) -> 2
            Random.nextInt(66) == Random.nextInt(66) -> 3
            Random.nextInt(3) == Random.nextInt(3) -> 4
            else -> 0
        }

        val results = mutableListOf<String>()
        if (rank > 0) {
            repeat(3) { results.add(rankPointKr[rank]!!) }
        }

        val failResults = mutableListOf<String>()
        while (failResults.size < 6 - results.size) {
            val result = rankPointKr[Random.nextInt(4) + 1]!!
            if (!results.contains(result) && failResults.count { it == result } < 2) failResults.add(result)
        }
        results.addAll(failResults)
        val point = rankPoint[rank]!!

        if (point > 0) pointCommandService.addPoint(user, point)

        gameLogRepository.save(
            GameLog(
                user = user,
                rank = rank,
                point = point,
                content = results.joinToString(","),
            )
        )

        return Speedo500Response(
            results = results.shuffled(),
            rank = rank,
            point = point,
        )
    }
}