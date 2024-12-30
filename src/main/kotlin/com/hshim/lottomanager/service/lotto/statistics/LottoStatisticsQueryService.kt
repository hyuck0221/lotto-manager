package com.hshim.lottomanager.service.lotto.statistics

import com.hshim.lottomanager.database.lotto.repository.LottoRepository
import com.hshim.lottomanager.model.lotto.statistics.LottoStatisticsNumberResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LottoStatisticsQueryService(
    private val lottoRepository: LottoRepository,
) {
    fun lottoStatisticsNumbers(): LottoStatisticsNumberResponse {
        val lottos = lottoRepository.findAllByIsOpenTrue()
        val numberCntMap = mutableMapOf<Int, Int>()
        (1..45).forEach { numberCntMap[it] = 0 }
        lottos.forEach { lotto ->
            lotto.numbers?.forEach { number ->
                numberCntMap[number] = numberCntMap[number]!! + 1
            }
            numberCntMap[lotto.bonusNumber!!] = numberCntMap[lotto.bonusNumber!!]!! + 1
        }
        return LottoStatisticsNumberResponse(numberCntMap)
    }
}