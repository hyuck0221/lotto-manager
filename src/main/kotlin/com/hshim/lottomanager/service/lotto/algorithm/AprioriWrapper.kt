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

        return (0..<cnt).map {
            val frequentItemsets = apriori(pastWinNumbers, detail.minSupportCnt)
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
        minSupportCnt: Int
    ): Map<Set<Int>, Int> {
        val itemsets = mutableMapOf<Set<Int>, Int>()

        // 1) 1-아이템셋(단일 숫자) 후보와 빈도수 계산
        val itemFreq = mutableMapOf<Int, Int>()
        for (txn in transactions) {
            for (item in txn) {
                itemFreq[item] = (itemFreq[item] ?: 0) + 1
            }
        }

        // 최소 지지도를 만족하는 1-아이템셋만 추린다
        var currentLevelSets = itemFreq
            .filter { it.value >= minSupportCnt }
            .map { setOf(it.key) to it.value }
            .toMap().toMutableMap()

        itemsets.putAll(currentLevelSets)

        // 2) k-아이템셋(점차 확장) 생성 반복
        var k = 2
        while (currentLevelSets.isNotEmpty()) {
            // 2-1) 후보 생성: (k-1)-아이템셋들로부터 k-아이템셋 후보를 만든다
            val candidates = generateCandidates(currentLevelSets.keys.toList(), k)

            // 2-2) 후보 빈도수 측정
            val candidateCounts = mutableMapOf<Set<Int>, Int>()
            for (txn in transactions) {
                val txnSet = txn.toSet()
                for (candidate in candidates) {
                    // 트랜잭션이 candidate를 모두 포함하면 카운트
                    if (candidate.all { it in txnSet }) {
                        candidateCounts[candidate] = (candidateCounts[candidate] ?: 0) + 1
                    }
                }
            }

            // 2-3) 최소 지지도를 만족하는 후보만 필터링
            currentLevelSets = candidateCounts
                .filter { it.value >= minSupportCnt }
                .toMutableMap()

            // 2-4) 전역 아이템셋에 추가
            itemsets.putAll(currentLevelSets)

            k++
        }

        return itemsets
    }

    /**
     * (k-1)-아이템셋들을 pairwise로 합쳐 k-아이템셋 후보를 생성
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
     * 빈발 아이템셋으로부터 연관 규칙 생성 (신뢰도 기준 필터링)
     */
    fun generateAssociationRules(
        frequentItemsets: Map<Set<Int>, Int>,
        transactions: List<List<Int>>,
        minConfidence: Double
    ): List<Triple<Set<Int>, Set<Int>, Double>> {
        val rules = mutableListOf<Triple<Set<Int>, Set<Int>, Double>>()
        val totalTxCount = transactions.size.toDouble()

        // 아이템셋 -> 지지도 맵
        val supportMap = frequentItemsets.mapValues { (_, count) ->
            count / totalTxCount
        }

        for ((itemset, _) in frequentItemsets) {
            if (itemset.size < 2) continue  // 1개 아이템셋은 규칙 불가

            // 모든 non-empty proper subset을 LHS로
            val subsets = getNonEmptySubsets(itemset)
            for (lhs in subsets) {
                if (lhs.size == itemset.size) continue
                val rhs = itemset - lhs

                // confidence = support(itemset) / support(lhs)
                val supportItemset = supportMap[itemset] ?: 0.0
                val supportLHS = getSupport(lhs, transactions)
                val confidence = if (supportLHS > 0.0) {
                    supportItemset / supportLHS
                } else 0.0

                if (confidence >= minConfidence) {
                    rules.add(Triple(lhs, rhs, confidence))
                }
            }
        }

        // 신뢰도 높은 순으로 정렬해 반환
        return rules.sortedByDescending { it.third }
    }

    /**
     * 부분집합(Proper Subset) 구하기
     */
    fun getNonEmptySubsets(set: Set<Int>): List<Set<Int>> {
        val result = mutableListOf<Set<Int>>()
        val list = set.toList()

        fun backtrack(index: Int, current: MutableList<Int>) {
            if (index == list.size) {
                if (current.isNotEmpty()) {
                    result.add(current.toSet())
                }
                return
            }
            // 원소 선택
            current.add(list[index])
            backtrack(index + 1, current)
            // 원소 미선택
            current.removeAt(current.size - 1)
            backtrack(index + 1, current)
        }

        backtrack(0, mutableListOf())
        return result
    }

    /**
     * itemset의 지원도(지지도) 계산
     */
    fun getSupport(itemset: Set<Int>, transactions: List<List<Int>>): Double {
        val count = transactions.count { txn -> itemset.all { it in txn } }
        return count.toDouble() / transactions.size
    }
}