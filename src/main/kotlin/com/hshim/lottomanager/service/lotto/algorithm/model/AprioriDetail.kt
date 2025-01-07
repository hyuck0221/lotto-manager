package com.hshim.lottomanager.service.lotto.algorithm.model

class AprioriDetail(
    val rangeRate: Double,
    val minSupportCnt: Int,
    val minConfidence: Double,
) : LottoAlgorithmDetail() {
    constructor(): this (
        rangeRate = 100.0,
        minSupportCnt = 2,
        minConfidence = 0.6,
    )

    fun getPageSize(totalCnt: Int): Int {
        return when {
            rangeRate >= 100 -> totalCnt
            rangeRate <= 0 -> 0
            else -> (totalCnt * (rangeRate / 100)).toInt()
        }
    }
}