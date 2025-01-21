package com.hshim.lottomanager.service.lotto.algorithm

import com.hshim.lottomanager.database.lotto.repository.LottoRepository
import com.hshim.lottomanager.enums.lotto.NumberBuildAlgorithm
import com.hshim.lottomanager.service.lotto.algorithm.model.AprioriDetail
import com.hshim.lottomanager.service.lotto.algorithm.model.LottoAlgorithmDetail
import com.hshim.lottomanager.util.ClassUtil.toClass
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class AprioriWrapper(
    private val lottoRepository: LottoRepository,
) : LottoNumberBuildImpl() {
    override fun support(algorithm: NumberBuildAlgorithm) = algorithm == NumberBuildAlgorithm.APRIORI
    override fun getDetail(detailMap: Map<String, Any>?): LottoAlgorithmDetail? {
        return when (detailMap == null) {
            true -> null
            false -> detailMap.toClass<AprioriDetail>()
        }
    }

    override fun build(cnt: Int, detail: LottoAlgorithmDetail?): List<List<Int>> {
        val detail = detail as? AprioriDetail ?: AprioriDetail()
        val lottoCnt = lottoRepository.count().toInt()
        val pageable = Pageable.ofSize(detail.getPageSize(lottoCnt))
        val pastWinNumbers = lottoRepository.findAllByTimesBeforeAndIsOpenTrue(
            times = detail.timesBefore,
            pageable = pageable
        ).map { it.numbers ?: emptyList() }

        val frequentItemsets = apriori(pastWinNumbers, detail.minSupportCnt)
        return (0..<cnt).map {
            val rules = generateAssociationRules(frequentItemsets, pastWinNumbers, detail.minConfidence)
            pick6NumbersFromRules(rules)
        }
    }

    fun pick6NumbersFromRules(
        rules: List<Triple<Set<Int>, Set<Int>, Double>>,
        maxNum: Int = 45
    ): List<Int> {
        // 신뢰도 높은 규칙부터 순회
        for ((lhs, rhs, conf) in rules.sortedByDescending { it.third }) {
            val unionSet = (lhs union rhs).toMutableSet()

            // 이미 6개 초과라면 스킵(다른 규칙 시도)
            if (unionSet.size > 6) {
                continue
            }

            // 부족하면 6개가 될 때까지 랜덤 보충
            while (unionSet.size < 6) {
                val candidate = Random.nextInt(1, maxNum + 1)
                unionSet.add(candidate)
            }

            // 오름차순 정렬 후 리턴
            return unionSet.sorted()
        }

        // 마땅한 규칙이 없거나 모두 6개 초과라면,
        // 완전 랜덤/또는 다른 로직으로 6개 생성(여기선 단순 랜덤 예시)
        return generateRandom6(maxNum)
    }

    /**
     * 1~maxNum 범위 안에서 무작위 6개 숫자 뽑기 (중복 없이)
     */
    fun generateRandom6(maxNum: Int = 45): List<Int> {
        val candidates = (1..maxNum).toMutableList()
        candidates.shuffle()
        return candidates.take(6).sorted()
    }

    /**
     * ---------------------------
     * 아래는 Apriori, 연관 규칙 관련 함수들
     * ---------------------------
     */

    /**
     * Apriori 알고리즘으로 빈발 아이템셋(Frequent Itemsets)을 찾는다.
     * @param transactions: 과거 당첨번호 리스트 (각 회차를 트랜잭션으로 본다)
     * @param minSupportCnt: 최소 지지도를 나타내는 '절대값'
     * @return Map<Set<Int>, Int> : (빈발 아이템셋, 해당 아이템셋 등장 횟수)
     */
    fun apriori(
        transactions: List<List<Int>>,
        minSupportCnt: Int,
        maxItemsetSize: Int = 3
    ): Map<Set<Int>, Int> {
        val transactionSets = transactions.map { it.toSet() }

        // 2) 1-아이템셋 수집
        val itemFreq = mutableMapOf<Int, Int>()
        for (txn in transactionSets) {
            for (item in txn) {
                itemFreq[item] = (itemFreq[item] ?: 0) + 1
            }
        }

        // 최소지지도를 만족하는 1-아이템셋만 추림
        var currentLevelSets: Map<Set<Int>, Int> = itemFreq
            .filter { it.value >= minSupportCnt }
            .map { setOf(it.key) to it.value }
            .toMap()

        // 최종적으로 return할 (빈발 아이템셋, 등장횟수)
        val itemsets = mutableMapOf<Set<Int>, Int>()
        itemsets.putAll(currentLevelSets)

        // 3) BFS 확장: k=2..maxItemsetSize 까지만
        for (k in 2..maxItemsetSize) {
            if (currentLevelSets.isEmpty()) break // 더 이상 확장 불가 시 중단

            // 3-1) 후보 생성
            val candidates = generateCandidates(currentLevelSets.keys.toList(), k)

            // 3-2) 후보별 등장횟수 계산
            val candidateCounts = mutableMapOf<Set<Int>, Int>()
            for (txn in transactionSets) {
                // txn이 candidate를 모두 포함하면 카운트 증가
                for (candidate in candidates) {
                    if (candidate.all { it in txn }) {
                        candidateCounts[candidate] = (candidateCounts[candidate] ?: 0) + 1
                    }
                }
            }

            // 3-3) 최소지지도를 만족하는 후보만 남김
            currentLevelSets = candidateCounts
                .filter { it.value >= minSupportCnt }
                .toMap()

            // 3-4) 전역 itemsets에 추가
            itemsets.putAll(currentLevelSets)
        }

        return itemsets
    }

    /**
     * (k-1)-아이템셋 목록으로부터 k-아이템셋 후보를 생성
     * - 단순히 2개씩 합치되, 합집합 크기가 k이면 후보로 채택
     */
    fun generateCandidates(prevLevel: List<Set<Int>>, k: Int): Set<Set<Int>> {
        val candidates = mutableSetOf<Set<Int>>()
        for (i in prevLevel.indices) {
            for (j in i + 1 until prevLevel.size) {
                val unionSet = prevLevel[i] union prevLevel[j]
                if (unionSet.size == k) {
                    candidates.add(unionSet)
                }
            }
        }
        return candidates
    }


    /**
     * 빈발 아이템셋에서 'LHS = 단일 아이템' 규칙만 생성
     *
     * @param frequentItemsets (아이템셋, 해당 아이템셋 등장횟수)
     * @param transactions 전체 트랜잭션 (support 계산용)
     * @param minConfidence 최소 신뢰도(Confidence) [0.0 ~ 1.0]
     */
    fun generateAssociationRules(
        frequentItemsets: Map<Set<Int>, Int>,
        transactions: List<List<Int>>,
        minConfidence: Double
    ): List<Triple<Set<Int>, Set<Int>, Double>> {
        val rules = mutableListOf<Triple<Set<Int>, Set<Int>, Double>>()
        val totalTxCount = transactions.size.toDouble()

        // 아이템셋 -> support(지지도) 미리 계산
        //   freq / 전체 트랜잭션 수
        val supportMap = frequentItemsets.mapValues { (_, freq) ->
            freq / totalTxCount
        }

        // 1) 각 빈발 아이템셋(itemset)마다
        for ((itemset, freqItemset) in frequentItemsets) {
            if (itemset.size < 2) continue  // 아이템 하나짜리는 규칙을 만들 수 없으므로 패스

            // 아이템셋의 support
            val supportItemset = freqItemset / totalTxCount

            // 2) LHS를 단일 아이템으로 고정
            //    itemset = {x1, x2, ..., xk}라면,
            //    각 xi를 LHS로 하고 (나머지) itemset - xi를 RHS로 하는 규칙 생성
            for (single in itemset) {
                val lhs = setOf(single)
                val rhs = itemset - lhs

                // LHS의 빈도(등장 횟수) -> 지지도
                val lhsFreq = frequentItemsets[lhs] ?: 0
                val supportLHS = lhsFreq / totalTxCount

                if (supportLHS > 0.0) {
                    val confidence = supportItemset / supportLHS
                    if (confidence >= minConfidence) {
                        // (LHS, RHS, 신뢰도) 형태로 저장
                        rules.add(Triple(lhs, rhs, confidence))
                    }
                }
            }
        }

        // 신뢰도 높은 순으로 정렬해서 반환
        return rules.sortedByDescending { it.third }
    }
}