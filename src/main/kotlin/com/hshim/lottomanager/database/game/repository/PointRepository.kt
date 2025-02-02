package com.hshim.lottomanager.database.game.repository

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.game.Point
import org.springframework.data.jpa.repository.JpaRepository

interface PointRepository : JpaRepository<Point, String> {
    fun findByUserId(userId: String): Point?
    fun findByUser(user: User): Point?
}