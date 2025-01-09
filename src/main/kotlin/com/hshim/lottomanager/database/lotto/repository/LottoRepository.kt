package com.hshim.lottomanager.database.lotto.repository

import com.hshim.lottomanager.database.lotto.Lotto
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface LottoRepository : JpaRepository<Lotto, Int> {
    fun findTopByIsOpenFalseOrderByTimesAsc(): Lotto?
    fun findTopByOrderByTimesDesc(): Lotto?
    fun findTopByIsOpenTrueOrderByTimesDesc(): Lotto?
    fun findAllByIsOpenTrue(): List<Lotto>
    fun findAllByTimesBeforeAndIsOpenTrue(
        times: Int,
        pageable: Pageable
    ): List<Lotto>
}