package com.hshim.lottomanager.service.lotto.algorithm

import com.hshim.lottomanager.database.lotto.repository.LottoRepository
import com.hshim.lottomanager.enums.lotto.NumberBuildAlgorithm
import com.hshim.lottomanager.service.lotto.algorithm.model.LottoAlgorithmDetail
import com.hshim.lottomanager.service.lotto.algorithm.model.MetaheuristicDetail
import com.hshim.lottomanager.util.ClassUtil.toClass
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class MetaheuristicWrapper(
    private val lottoRepository: LottoRepository,
) : LottoNumberBuildImpl() {
    override fun support(algorithm: NumberBuildAlgorithm) = algorithm == NumberBuildAlgorithm.METAHEURISTIC
    override fun getDetail(detailMap: Map<String, Any>?): LottoAlgorithmDetail? {
        return when (detailMap == null) {
            true -> null
            false -> detailMap.toClass<MetaheuristicDetail>()
        }
    }

    override fun build(cnt: Int, detail: LottoAlgorithmDetail?): List<List<Int>> {
        val detail = detail as? MetaheuristicDetail ?: MetaheuristicDetail()
        val lottoCnt = lottoRepository.count().toInt()
        val pageable = Pageable.ofSize(detail.getPageSize(lottoCnt))
        val pastWinNumbers = lottoRepository.findAllByTimesBeforeAndIsOpenTrue(
            times = detail.timesBefore,
            pageable = pageable
        ).map { it.numbers ?: emptyList() }

        val freq = IntArray(46)
        for (draw in pastWinNumbers) {
            for (num in draw) {
                freq[num]++
            }
        }

        val weightedList = mutableListOf<Int>()
        for (num in 1..45) {
            repeat(freq[num]) {
                weightedList.add(num)
            }
        }

        for (num in 1..45) {
            if (freq[num] == 0) {
                weightedList.add(num)
            }
        }

        return (0..<cnt).map {
            val thisWeightedList = weightedList.toMutableList()
            (1..6)
                .map {
                    val number = thisWeightedList.random(Random)
                    thisWeightedList.removeAll { it == number }
                    number
                }
                .numberSort()
        }
    }
}