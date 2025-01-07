package com.hshim.lottomanager.service.lotto

import com.hshim.lottomanager.database.lotto.repository.LottoNumberRepository
import com.hshim.lottomanager.enums.lotto.NumberBuildAlgorithm
import com.hshim.lottomanager.exception.GlobalException
import com.hshim.lottomanager.model.lotto.LottoNumberBuildRequest
import com.hshim.lottomanager.model.lotto.LottoNumberBuildResponse
import com.hshim.lottomanager.model.lotto.LottoNumberRequest
import com.hshim.lottomanager.model.lotto.LottoNumberResponse
import com.hshim.lottomanager.service.account.user.UserQueryService
import com.hshim.lottomanager.service.lotto.algorithm.LottoNumberBuildWrapper
import com.hshim.lottomanager.service.qr.QRManager
import com.hshim.lottomanager.util.QueueUtil.polls
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
@Transactional(readOnly = true)
class LottoNumberQueryService(
    private val qrManager: QRManager,
    private val userQueryService: UserQueryService,
    private val lottoNumberRepository: LottoNumberRepository,
    private val LottoNumberBuildWrappers: List<LottoNumberBuildWrapper>,
) {
    private val separator = "v="
    private val parameterSeparators = listOf('q', 'm')

    fun getRequests(photos: List<MultipartFile>): List<LottoNumberRequest> {
        val records = photos.flatMap { photo ->
            qrManager.decode(photo)
                .mapNotNull { url ->
                    when (val index = url.lastIndexOf(separator)) {
                        -1 -> null
                        else -> url.substring(index + separator.length, url.length)
                    }
                }
        }

        return records.flatMap { record ->
            val queue: Queue<Char> = LinkedList(record.toList())
            val times = queue.polls(4).toIntOrNull() ?: throw GlobalException.NOT_FOUND_TIMES.exception
            val numbersList = mutableListOf<List<Int>>()
            while (queue.size > 12) {
                if (!parameterSeparators.contains(queue.poll())) continue
                val numbers = (1..6).map { queue.polls(2).toIntOrNull() ?: 0 }
                numbersList.add(numbers)
            }
            numbersList.map { LottoNumberRequest(times, it) }
        }
    }

    fun getNumbers(): List<LottoNumberResponse> {
        val user = userQueryService.getUser()
        return lottoNumberRepository.findAllByUserIdOrderByLottoTimesDescCreateDateDesc(user.id)
            .map { LottoNumberResponse(it) }
    }

    fun numbersBuild(request: LottoNumberBuildRequest): List<LottoNumberBuildResponse> {
        val wrapper = LottoNumberBuildWrappers.find { it.support(request.algorithm) }
            ?: throw GlobalException.NOT_FOUND_TIMES.exception
        val detail = wrapper.getDetail(request.detail)
        val numbersList = wrapper.build(request.cnt, detail)
        return numbersList.map { LottoNumberBuildResponse(it) }
    }
}