package com.hshim.lottomanager.service.lotto

import com.hshim.lottomanager.database.lotto.Lotto
import com.hshim.lottomanager.database.lotto.repository.LottoRepository
import com.hshim.lottomanager.service.lotto.template.LottoTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LottoCommandService(
    @Value(value = "\${open-api.lotto.info.url}")
    private val url: String,
    private val lottoRepository: LottoRepository,
) {
    fun init(times: Int): Lotto {
        val lotto = lottoRepository.findByIdOrNull(times)
            ?: LottoTemplate(times, url).getInfo()?.toEntity()
            ?: Lotto(times)
        return lottoRepository.save(lotto)
    }

    fun open(lotto: Lotto): Boolean {
        LottoTemplate(lotto.times, url).getInfo()
            ?.apply { this.updateTo(lotto) }
            ?: return false
        return true
    }
}