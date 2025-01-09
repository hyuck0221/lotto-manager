package com.hshim.lottomanager.service.lotto.algorithm.model

class MetaheuristicDetail(
    val rangeRate: Double
) : LottoAlgorithmDetail() {
    constructor(): this (
        rangeRate = 100.0
    )

    fun getPageSize(totalCnt: Int): Int {
        return when {
            rangeRate >= 100 -> totalCnt
            rangeRate <= 0 -> 0
            else -> (totalCnt * (rangeRate / 100)).toInt()
        }
    }
}