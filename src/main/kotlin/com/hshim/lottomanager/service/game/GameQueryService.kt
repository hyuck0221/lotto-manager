package com.hshim.lottomanager.service.game

import com.hshim.lottomanager.enums.game.Game
import com.hshim.lottomanager.model.game.GameResponse
import com.hshim.lottomanager.model.game.Speedo500Response
import com.hshim.lottomanager.model.game.SpeedoResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GameQueryService {
    fun games(search: String?): List<GameResponse> {
        return Game.entries
            .filter { it.title.contains(search ?: "") || it.description.contains(search ?: "") }
            .map { GameResponse(it) }
    }
}