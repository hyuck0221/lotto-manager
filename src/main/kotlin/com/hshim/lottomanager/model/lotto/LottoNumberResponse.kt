package com.hshim.lottomanager.model.lotto

import com.hshim.lottomanager.database.lotto.LottoNumber

class LottoNumberResponse(
    val numbers: List<Int>,
    val rank: Int?,
    val lotto: LottoResponse,
) {
    constructor(lottoNumber: LottoNumber): this (
        numbers = lottoNumber.numbers,
        rank = lottoNumber.rank,
        lotto = LottoResponse(lottoNumber.lotto),
    )
}