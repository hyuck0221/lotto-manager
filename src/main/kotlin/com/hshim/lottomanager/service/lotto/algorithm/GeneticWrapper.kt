package com.hshim.lottomanager.service.lotto.algorithm

import com.hshim.lottomanager.database.lotto.repository.LottoRepository
import com.hshim.lottomanager.enums.lotto.NumberBuildAlgorithm
import com.hshim.lottomanager.service.lotto.algorithm.model.GeneticDetail
import com.hshim.lottomanager.service.lotto.algorithm.model.LottoAlgorithmDetail
import com.hshim.lottomanager.service.lotto.algorithm.model.LottoChromosome
import com.hshim.lottomanager.service.lotto.algorithm.model.MetaheuristicDetail
import com.hshim.lottomanager.util.ClassUtil.toClass
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class GeneticWrapper(
    private val lottoRepository: LottoRepository,
) : LottoNumberBuildImpl() {
    override fun support(algorithm: NumberBuildAlgorithm) = algorithm == NumberBuildAlgorithm.GENETIC
    override fun getDetail(detailMap: Map<String, Any>?): LottoAlgorithmDetail? {
        return when (detailMap == null) {
            true -> null
            false -> detailMap.toClass<GeneticDetail>()
        }
    }

    override fun build(cnt: Int, detail: LottoAlgorithmDetail?): List<List<Int>> {
        val detail = detail as? GeneticDetail ?: GeneticDetail()
        val lottoCnt = lottoRepository.count().toInt()
        val pageable = Pageable.ofSize(detail.getPageSize(lottoCnt))
        val pastWinNumbers = lottoRepository.findAllByIsOpenTrue(pageable).map { it.numbers ?: emptyList() }

        var population = generatePopulation(detail)

        return (0..<cnt).map {
            repeat(detail.generations) {
                val fitnessScores = population.map { calculateFitness(it, pastWinNumbers) }
                val matingPool = selection(population, fitnessScores, detail.populationSize)
                val newPopulation = mutableListOf<LottoChromosome>()

                // 교차(Crossover)
                while (newPopulation.size < detail.populationSize) {
                    val parent1 = matingPool.random()
                    val parent2 = matingPool.random()

                    if (Random.nextDouble() < detail.crossoverRate) {
                        val (child1, child2) = crossover(parent1, parent2)
                        newPopulation.add(child1)
                        newPopulation.add(child2)
                    } else {
                        newPopulation.add(parent1)
                        newPopulation.add(parent2)
                    }
                }

                // 돌연변이(Mutation)
                val mutatedPopulation = newPopulation.map { child ->
                    if (Random.nextDouble() < detail.mutationRate) {
                        mutation(child)
                    } else {
                        child
                    }
                }

                population = mutatedPopulation
            }

            // 최종 세대에서 가장 적합도 높은 해 반환
            val finalFitness = population.map { calculateFitness(it, pastWinNumbers) }
            val bestIndex = finalFitness.indices.maxByOrNull { finalFitness[it] } ?: 0

            population[bestIndex].sorted()
        }
    }
}

private fun generatePopulation(detail: GeneticDetail): List<LottoChromosome> {
    return List(detail.populationSize) { generateRandomLotto() }
}

private fun generateRandomLotto(): LottoChromosome {
    val numbers = (1..45).toMutableList()
    numbers.shuffle()
    return numbers.take(6)
}

private fun calculateFitness(
    chromosome: LottoChromosome,
    pastWinNumbers: List<List<Int>>,
): Double {
    val freq = IntArray(46)
    for (draw in pastWinNumbers) {
        for (num in draw) {
            freq[num]++
        }
    }

    var score = 0.0
    for (num in chromosome) {
        score += freq[num]
    }
    return score
}

private fun selection(
    population: List<LottoChromosome>,
    fitnessScores: List<Double>,
    populationSize: Int
): List<LottoChromosome> {
    val selected = mutableListOf<LottoChromosome>()

    val totalFitness = fitnessScores.sum()
    if (totalFitness <= 0.0) {
        return population.shuffled().take(populationSize)
    }

    repeat(populationSize) {
        val r = Random.nextDouble() * totalFitness
        var cumulative = 0.0
        for ((index, score) in fitnessScores.withIndex()) {
            cumulative += score
            if (cumulative >= r) {
                selected.add(population[index])
                break
            }
        }
    }

    return selected
}

fun crossover(parent1: LottoChromosome, parent2: LottoChromosome): Pair<LottoChromosome, LottoChromosome> {
    val crossoverPoint = Random.nextInt(1, 5)
    val child1 = parent1.take(crossoverPoint) + parent2.takeLast(6 - crossoverPoint)
    val child2 = parent2.take(crossoverPoint) + parent1.takeLast(6 - crossoverPoint)

    return Pair(
        fixChromosome(child1),
        fixChromosome(child2)
    )
}

fun mutation(chromosome: LottoChromosome): LottoChromosome {
    val mutated = chromosome.toMutableList()
    val mutateIndex = Random.nextInt(6)
    val newNum = generateRandomLotto().first()
    mutated[mutateIndex] = newNum

    return fixChromosome(mutated)
}

fun fixChromosome(candidate: List<Int>): LottoChromosome {
    val cleaned = candidate.filter { it in 1..45 }.distinct().toMutableList()

    while (cleaned.size < 6) {
        val newNum = Random.nextInt(1, 46)
        if (!cleaned.contains(newNum)) {
            cleaned.add(newNum)
        }
    }

    return cleaned.take(6)
}