package com.hshim.lottomanager.model.lotto

import com.hshim.lottomanager.database.lotto.LottoNumber

class LottoNumberResponse(
    val id: String,
    val numbers: List<Int>,
    val rank: Int?,
    val lotto: LottoResponse,
    val note: String?,
) {
    constructor(lottoNumber: LottoNumber): this (
        id = lottoNumber.id,
        numbers = lottoNumber.numbers,
        rank = lottoNumber.rank,
        lotto = LottoResponse(lottoNumber.lotto),
        note = lottoNumber.note,
    )
}