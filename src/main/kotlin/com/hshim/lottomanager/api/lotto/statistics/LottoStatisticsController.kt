package com.hshim.lottomanager.api.lotto.statistics

import com.hshim.lottomanager.model.lotto.statistics.LottoStatisticsNumberResponse
import com.hshim.lottomanager.service.lotto.statistics.LottoStatisticsQueryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/lotto/statistics")
class LottoStatisticsController(private val lottoStatisticsQueryService: LottoStatisticsQueryService) {
    @GetMapping("/numbers")
    fun lottoStatisticsNumbers(): LottoStatisticsNumberResponse {
        return lottoStatisticsQueryService.lottoStatisticsNumbers()
    }
}