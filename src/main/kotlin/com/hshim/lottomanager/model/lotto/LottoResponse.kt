package com.hshim.lottomanager.model.lotto

import com.hshim.lottomanager.database.lotto.Lotto
import java.time.LocalDate

class LottoResponse(
    val times: Int,
    val openDate: LocalDate?,
    val isOpen: Boolean,
    val numbers: List<Int>?,
    val bonusNumber: Int?,
    val totalPrize: Long?,
    val firstWinnerPrize: Long?,
    val winCnt: Int?,
) {
    constructor(lotto: Lotto): this (
        times = lotto.times,
        openDate = lotto.openDate,
        isOpen = lotto.isOpen,
        numbers = lotto.numbers,
        bonusNumber = lotto.bonusNumber,
        totalPrize = lotto.totalPrize,
        firstWinnerPrize = lotto.firstWinnerPrize,
        winCnt = lotto.winCnt,
    )
}