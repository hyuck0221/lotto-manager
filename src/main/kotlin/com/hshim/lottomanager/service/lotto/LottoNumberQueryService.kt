package com.hshim.lottomanager.service.lotto

import com.hshim.lottomanager.exception.GlobalException
import com.hshim.lottomanager.model.lotto.LottoNumberRequest
import com.hshim.lottomanager.service.qr.QRManager
import com.hshim.lottomanager.util.QueueUtil.polls
import com.hshim.lottomanager.util.QueueUtil.throwPolls
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
@Transactional(readOnly = true)
class LottoNumberQueryService(
    private val qrManager: QRManager,
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
}