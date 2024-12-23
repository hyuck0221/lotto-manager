package com.hshim.lottomanager.model.lotto

import com.fasterxml.jackson.annotation.JsonProperty
import com.hshim.lottomanager.database.lotto.Lotto
import io.autocrypt.sakarinblue.universe.util.DateUtil.stringToDate
import java.time.LocalDate

class LottoTemplateResponse(
    val returnValue: String,
    @get:JsonProperty("totSellamnt")
    val totalPrize: Long,
    @get:JsonProperty("drwNoDate")
    val openDate: String,
    @get:JsonProperty("firstWinamnt")
    val firstWinnerPrizeByWinCnt: Long,
    @get:JsonProperty("firstAccumamnt")
    val firstWinnerPrize: Long,
    @get:JsonProperty("firstPrzwnerCo")
    val winCnt: Int,
    @get:JsonProperty("drwNo")
    val times: Int,
    @get:JsonProperty("drwtNo1")
    val number1: Int,
    @get:JsonProperty("drwtNo2")
    val number2: Int,
    @get:JsonProperty("drwtNo3")
    val number3: Int,
    @get:JsonProperty("drwtNo4")
    val number4: Int,
    @get:JsonProperty("drwtNo5")
    val number5: Int,
    @get:JsonProperty("drwtNo6")
    val number6: Int,
    @get:JsonProperty("bnusNo")
    val bonusNumber: Int,
) {
    val numbers = listOf(number1, number2, number3, number4, number5, number6)

    fun toEntity() = Lotto(
        times = times,
        openDate = openDate.stringToDate(),
        totalPrize = totalPrize,
        firstWinnerPrize = firstWinnerPrize,
        bonusNumber = bonusNumber,
        winCnt = winCnt,
        numbers = numbers,
        isOpen = true,
    )

    fun updateTo(lotto: Lotto) {
        lotto.openDate = this.openDate.stringToDate()
        lotto.numbers = this.numbers
        lotto.bonusNumber = this.bonusNumber
        lotto.totalPrize = this.totalPrize
        lotto.firstWinnerPrize = this.firstWinnerPrize
        lotto.winCnt = this.winCnt
        lotto.isOpen = true
    }
}