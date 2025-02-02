package com.hshim.lottomanager.database.game.repository

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.game.DailyCheck
import org.springframework.data.jpa.repository.JpaRepository

interface DailyCheckRepository : JpaRepository<DailyCheck, String> {
    fun findTopByUserOrderByCreateDateDesc(user: User): DailyCheck?
    fun countAllByUser(user: User): Long
}