package com.hshim.lottomanager.model.lotto

import com.hshim.lottomanager.enums.lotto.NumberBuildAlgorithm

class LottoNumberBuildRequest(
    val algorithm: NumberBuildAlgorithm,
    val cnt: Int,
    var detail: MutableMap<String, Any>?,
    val progressingId: String,
)