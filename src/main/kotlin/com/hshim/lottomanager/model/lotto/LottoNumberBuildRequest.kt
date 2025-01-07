package com.hshim.lottomanager.model.lotto

import com.hshim.lottomanager.enums.lotto.NumberBuildAlgorithm

class LottoNumberBuildRequest(
    val algorithm: NumberBuildAlgorithm,
    val cnt: Int,
    val detail: Map<String, Any>?,
)