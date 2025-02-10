package com.hshim.lottomanager.service.lotto.algorithm

import com.hshim.lottomanager.enums.lotto.NumberBuildAlgorithm
import com.hshim.lottomanager.service.emitter.model.EmitterEventModel
import com.hshim.lottomanager.service.lotto.algorithm.model.LottoAlgorithmDetail
import com.hshim.lottomanager.service.lotto.algorithm.model.MetaheuristicDetail
import com.hshim.lottomanager.service.lotto.algorithm.model.SimpleRandomDetail
import com.hshim.lottomanager.service.progressing.ProgressingService
import com.hshim.lottomanager.util.ClassUtil.toClass
import org.springframework.stereotype.Component
import java.util.*

@Component
class SimpleRandomWrapper(progressingService: ProgressingService) : LottoNumberBuildImpl(progressingService) {
    override fun support(algorithm: NumberBuildAlgorithm) = algorithm == NumberBuildAlgorithm.SIMPLE_RANDOM
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
        val detail = detail as? SimpleRandomDetail ?: SimpleRandomDetail()
        val progressingModel = EmitterEventModel.ProcessingInfo(
            id = progressingId,
            description = "랜덤 생성 중",
            totalCnt = 1,
        )
        val totalCnt = cnt * 6
        var finish = 0
        progressingModel.updatePercent(totalCnt, finish)

        val result = (0..<cnt).map {
            val numbers = mutableListOf<Int>()
            (1..6).forEach { _ ->
                var number = Random().nextInt(45) + 1
                while (numbers.contains(number)) {
                    number = Random().nextInt(45) + 1
                }
                numbers.add(number)
                progressingModel.updatePercent(totalCnt, ++finish)
            }
            numbers.numberSort()
        }

        progressingModel.complete()
        return result
    }
}