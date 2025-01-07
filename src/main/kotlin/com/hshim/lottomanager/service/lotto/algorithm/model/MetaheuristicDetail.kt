package com.hshim.lottomanager.service.lotto.algorithm.model

class MetaheuristicDetail(
    val rangeRate: Double = 100.0,
) : LottoAlgorithmDetail() {

    fun getPageSize(totalCnt: Int): Int {
        return when {
            rangeRate >= 100 -> totalCnt
            rangeRate <= 0 -> 0
            else -> (totalCnt * (rangeRate / 100)).toInt()
        }
    }
}