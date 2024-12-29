package com.hshim.lottomanager.database.lotto.repository

import com.hshim.lottomanager.database.lotto.Lotto
import com.hshim.lottomanager.database.lotto.LottoNumber
import org.springframework.data.jpa.repository.JpaRepository

interface LottoNumberRepository : JpaRepository<LottoNumber, String> {
    fun findAllByUserIdOrderByLottoTimesDescCreateDateDesc(userId: String): List<LottoNumber>
    fun findAllByLotto(lotto: Lotto): List<LottoNumber>
}