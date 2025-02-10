package com.hshim.lottomanager.service.lotto.algorithm

import com.hshim.lottomanager.database.lotto.AlgorithmTestLog
import com.hshim.lottomanager.database.lotto.repository.AlgorithmTestLogRepository
import com.hshim.lottomanager.database.lotto.repository.LottoRepository
import com.hshim.lottomanager.enums.lotto.NumberBuildAlgorithm
import com.hshim.lottomanager.exception.GlobalException
import com.hshim.lottomanager.model.lotto.LottoNumberBuildRequest
import com.hshim.lottomanager.service.lotto.LottoNumberQueryService
import io.autocrypt.sakarinblue.universe.util.CommonUtil.uuid
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AlgorithmTestService(
    private val lottoNumberQueryService: LottoNumberQueryService,
    private val lottoRepository: LottoRepository,
    private val algorithmTestLogRepository: AlgorithmTestLogRepository,
) {
    fun test(
        times: Int,
        request: LottoNumberBuildRequest,
        taskId: String,
    ) {
        val lotto = lottoRepository.findByIdOrNull(times)
            ?.takeIf { it.isOpen }
            ?: throw GlobalException.NOT_FOUND_TIMES.exception

        request.detail?.let { it["timesBefore"] = times }
            ?: { request.detail = mutableMapOf("timesBefore" to times) }

        val testLogs = lottoNumberQueryService.numbersBuild(request)
            .map {
                AlgorithmTestLog(
                    lotto = lotto,
                    taskId = taskId,
                    numbers = it.numbers,
                    algorithm = request.algorithm,
                    request = request.detail?.toString(),
                )
            }

        algorithmTestLogRepository.saveAll(testLogs)
    }

    fun testAll(times: Int, cnt: Int) {
        val taskId = uuid()
        NumberBuildAlgorithm.entries.filter { !it.disable }.forEach {
            val request = LottoNumberBuildRequest(
                algorithm = it,
                cnt = cnt,
                detail = null,
                progressingId = "test",
            )
            test(times, request, taskId)
        }
    }
}