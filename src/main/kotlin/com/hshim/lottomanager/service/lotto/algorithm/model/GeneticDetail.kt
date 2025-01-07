package com.hshim.lottomanager.service.lotto.algorithm.model

data class GeneticDetail(
    val rangeRate: Double,
    val generations: Int,
    val mutationRate: Double,
    val populationSize: Int,
    val crossoverRate: Double,
) : LottoAlgorithmDetail() {
    constructor() : this(
        rangeRate = 30.0,
        generations = 50,
        mutationRate = 5.0,
        populationSize = 10,
        crossoverRate = 0.8,
    )

    fun getPageSize(totalCnt: Int): Int {
        return when {
            rangeRate >= 100 -> totalCnt
            rangeRate <= 0 -> 0
            else -> (totalCnt * (rangeRate / 100)).toInt()
        }
    }
}

typealias LottoChromosome = List<Int>