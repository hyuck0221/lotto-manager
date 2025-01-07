package com.hshim.lottomanager.service.lotto.algorithm

import com.hshim.lottomanager.enums.lotto.NumberBuildAlgorithm
import com.hshim.lottomanager.service.lotto.algorithm.model.LottoAlgorithmDetail

interface LottoNumberBuildWrapper {
    fun support(algorithm: NumberBuildAlgorithm): Boolean
    fun getDetail(detailMap: Map<String, Any>?): LottoAlgorithmDetail?
    fun build(cnt: Int, detail: LottoAlgorithmDetail?): List<List<Int>>
}