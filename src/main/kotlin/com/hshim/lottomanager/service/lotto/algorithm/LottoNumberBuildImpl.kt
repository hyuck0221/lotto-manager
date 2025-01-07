package com.hshim.lottomanager.service.lotto.algorithm

abstract class LottoNumberBuildImpl : LottoNumberBuildWrapper {
    fun List<Int>.numberSort() = this.sorted()
}