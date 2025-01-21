package com.hshim.lottomanager.api.config

import com.hshim.lottomanager.enums.lotto.NumberBuildAlgorithm
import com.hshim.lottomanager.enums.question.QuestionType
import com.hshim.lottomanager.model.config.ConfigResponse
import com.hshim.lottomanager.model.config.NumberBuildAlgorithmResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/config")
class ConfigController(
) {
    @GetMapping("/number/build/algorithms")
    fun getNumberBuildAlgorithms(): List<NumberBuildAlgorithmResponse> {
        return NumberBuildAlgorithm.entries.filter { !it.disable }.map { NumberBuildAlgorithmResponse(it) }
    }
    @GetMapping("/question/types")
    fun getQuestionTypes(): List<ConfigResponse> {
        return QuestionType.entries.map { ConfigResponse(it.name, it.description) }
    }
}