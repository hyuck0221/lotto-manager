package com.hshim.lottomanager.database.game.repository

import com.hshim.lottomanager.database.game.GameLog
import org.springframework.data.jpa.repository.JpaRepository

interface GameLogRepository : JpaRepository<GameLog, String> {
}