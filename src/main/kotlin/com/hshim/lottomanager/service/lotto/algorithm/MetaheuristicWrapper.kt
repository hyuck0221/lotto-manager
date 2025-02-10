package com.hshim.lottomanager.service.lotto.algorithm

import com.hshim.lottomanager.database.lotto.repository.LottoRepository
import com.hshim.lottomanager.enums.lotto.NumberBuildAlgorithm
import com.hshim.lottomanager.service.emitter.model.EmitterEventModel
import com.hshim.lottomanager.service.lotto.algorithm.model.LottoAlgorithmDetail
import com.hshim.lottomanager.service.lotto.algorithm.model.MetaheuristicDetail
import com.hshim.lottomanager.service.progressing.ProgressingService
import com.hshim.lottomanager.util.ClassUtil.toClass
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class MetaheuristicWrapper(
    private val lottoRepository: LottoRepository,
    progressingService: ProgressingService,
) : LottoNumberBuildImpl(progressingService) {
    override fun support(algorithm: NumberBuildAlgorithm) = algorithm == NumberBuildAlgorithm.METAHEURISTIC
    override fun getDetail(detailMap: Map<String, Any>?): LottoAlgorithmDetail? {
        return when (detailMap == null) {
            true -> null
            false -> detailMap.toClass<MetaheuristicDetail>()
        }
    }

    override fun build(
        cnt: Int,
        progressingId: String,
        detail: LottoAlgorithmDetail?,
    ): List<List<Int>> {
        val detail = detail as? MetaheuristicDetail ?: MetaheuristicDetail()
        val lottoCnt = lottoRepository.count().toInt()
        val progressingModel = EmitterEventModel.ProcessingInfo(
            id = progressingId,
            description = "지난 당첨번호 불러오는 중",
            totalCnt = 4,
        )
        var totalCnt = 1
        var finish = 0
        progressingModel.updatePercent(totalCnt, finish)

        val pageable = Pageable.ofSize(detail.getPageSize(lottoCnt))
        val pastWinNumbers = lottoRepository.findAllByTimesBeforeAndIsOpenTrue(
            times = detail.timesBefore,
            pageable = pageable
        ).map { it.numbers ?: emptyList() }

        totalCnt = 45 * pastWinNumbers.size
        finish = 0
        progressingModel.apply {
            this.description = "지난 당첨이력 정리 중"
            this.executeCnt++
        }.updatePercent(totalCnt, finish)

        val freq = IntArray(46)
        for (draw in pastWinNumbers) {
            for (num in draw) {
                freq[num]++
            }
            progressingModel.updatePercent(totalCnt, ++finish)
        }

        totalCnt = 45
        finish = 0
        progressingModel.apply {
            this.description = "지난 당첨이력 최적화 중"
            this.executeCnt++
        }.updatePercent(totalCnt, finish)

        val weightedList = mutableListOf<Int>()
        for (num in 1..45) {
            repeat(freq[num]) {
                weightedList.add(num)
            }
            progressingModel.updatePercent(totalCnt, ++finish)
        }

        for (num in 1..45) {
            if (freq[num] == 0) {
                weightedList.add(num)
            }
        }

        totalCnt = cnt * 6
        finish = 0
        progressingModel.apply {
            this.description = "당첨이력을 참고하여 랜덤 생성 중"
            this.executeCnt++
        }.updatePercent(totalCnt, finish)

        val result = (0..<cnt).map {
            val thisWeightedList = weightedList.toMutableList()
            (1..6)
                .map {
                    val number = thisWeightedList.random(Random)
                    thisWeightedList.removeAll { it == number }
                    progressingModel.updatePercent(totalCnt, ++finish)
                    number
                }
                .numberSort()
        }

        progressingModel.complete()
        return result
    }
}