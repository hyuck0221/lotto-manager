package com.hshim.lottomanager.model.lotto

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.lotto.Lotto
import com.hshim.lottomanager.database.lotto.LottoNumber

class LottoNumberResponse(
    val numbers: List<Int>,
    val lotto: LottoResponse,
) {
    fun toEntity(
        lotto: Lotto,
        user: User,
    ) = LottoNumber(
        lotto = lotto,
        user = user,
        numbers = numbers,
    )

    constructor(lottoNumber: LottoNumber): this (
        numbers = lottoNumber.numbers,
        lotto = LottoResponse(lottoNumber.lotto),
    )
}