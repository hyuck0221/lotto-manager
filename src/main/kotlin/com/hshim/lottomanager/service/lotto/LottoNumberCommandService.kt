package com.hshim.lottomanager.service.lotto

import com.hshim.lottomanager.database.lotto.repository.LottoNumberRepository
import com.hshim.lottomanager.model.lotto.LottoNumberRequest
import com.hshim.lottomanager.service.account.user.UserQueryService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LottoNumberCommandService(
    private val lottoNumberRepository: LottoNumberRepository,
    private val lottoCommandService: LottoCommandService,
    private val userQueryService: UserQueryService,
) {
    fun init(request: LottoNumberRequest) {
        val user = userQueryService.getUser()
        val lotto = lottoCommandService.init(request.times)
        lottoNumberRepository.save(request.toEntity(lotto, user))
    }

    fun init(requests: List<LottoNumberRequest>) {
        val user = userQueryService.getUser()
        val timesToRequests = requests.groupBy { it.times }
        timesToRequests.forEach { (times, requests) ->
            val lotto = lottoCommandService.init(times)
            lottoNumberRepository.saveAll(requests.map { it.toEntity(lotto, user) })
        }
    }

    fun delete(id: String) {
        lottoNumberRepository.deleteById(id)
    }
}