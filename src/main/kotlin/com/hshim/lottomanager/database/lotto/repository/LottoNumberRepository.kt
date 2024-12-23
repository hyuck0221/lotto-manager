package com.hshim.lottomanager.database.lotto.repository

import com.hshim.lottomanager.database.lotto.LottoNumber
import org.springframework.data.jpa.repository.JpaRepository

interface LottoNumberRepository : JpaRepository<LottoNumber, String> {
}