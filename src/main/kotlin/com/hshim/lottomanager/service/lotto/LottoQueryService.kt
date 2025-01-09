package com.hshim.lottomanager.service.lotto

import com.hshim.lottomanager.database.lotto.repository.LottoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LottoQueryService(
    private val lottoRepository: LottoRepository,
) {
    fun timesLatest(): Map<String, Int> {
        val latestLotto = lottoRepository.findTopByIsOpenTrueOrderByTimesDesc()
            ?: return emptyMap()
        return mapOf("times" to latestLotto.times)
    }
}