package com.hshim.lottomanager.scheduler.lotto

import com.hshim.lottomanager.database.lotto.Lotto
import com.hshim.lottomanager.database.lotto.repository.LottoNumberRepository
import com.hshim.lottomanager.database.lotto.repository.LottoRepository
import com.hshim.lottomanager.service.lotto.LottoCommandService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class LottoScheduler(
    private val lottoCommandService: LottoCommandService,
    private val lottoRepository: LottoRepository,
    private val lottoNumberRepository: LottoNumberRepository,
) {
    @Scheduled(cron = "0 0 21 ? * SAT", zone = "Asia/Seoul")
    fun openLotto() {
        val lastLotto = lottoRepository.findTopByOrderByTimesDesc()
            ?: return
        val lotto = when (lastLotto.isOpen) {
            true -> Lotto(lastLotto.times + 1)
            false -> lastLotto
        }

        lottoCommandService.open(lotto)

        lottoNumberRepository.findAllByLotto(lotto)
            .groupBy { it.user }
            .forEach { user, lottoNumbers ->
                lottoNumbers.forEach { it.setRank() }
                if (user.sendType != null)
            }

        lottoRepository.save(lotto)
    }
}