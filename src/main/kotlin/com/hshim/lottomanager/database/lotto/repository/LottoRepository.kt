package com.hshim.lottomanager.database.lotto.repository

import com.hshim.lottomanager.database.lotto.Lotto
import org.springframework.data.jpa.repository.JpaRepository

interface LottoRepository : JpaRepository<Lotto, Int> {
    fun findByTimes(times: Int): Lotto?
}