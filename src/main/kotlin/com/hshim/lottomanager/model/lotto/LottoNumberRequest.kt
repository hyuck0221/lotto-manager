package com.hshim.lottomanager.model.lotto

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.lotto.Lotto
import com.hshim.lottomanager.database.lotto.LottoNumber

class LottoNumberRequest(
    val times: Int,
    val numbers: List<Int>,
    val note: String?,
) {
    fun toEntity(lotto: Lotto, user: User) = LottoNumber(
        lotto = lotto,
        user = user,
        numbers = numbers,
        note = note,
    )
}