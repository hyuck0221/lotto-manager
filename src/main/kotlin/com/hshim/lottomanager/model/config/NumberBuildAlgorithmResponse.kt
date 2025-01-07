package com.hshim.lottomanager.model.config

import com.hshim.lottomanager.enums.lotto.NumberBuildAlgorithm

class NumberBuildAlgorithmResponse(
    key: String,
    value: String,
    val description: String,
) : ConfigResponse(key, value) {
    constructor(numberBuildAlgorithm: NumberBuildAlgorithm): this (
        key = numberBuildAlgorithm.name,
        value = numberBuildAlgorithm.title,
        description = numberBuildAlgorithm.description,
    )
}