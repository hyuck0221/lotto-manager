package com.hshim.lottomanager.database.game.repository

import com.hshim.lottomanager.database.game.PointLog
import org.springframework.data.jpa.repository.JpaRepository

interface PointLogRepository : JpaRepository<PointLog, String> {
}