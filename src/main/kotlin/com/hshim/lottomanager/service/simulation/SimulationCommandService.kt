package com.hshim.lottomanager.service.simulation

import com.hshim.lottomanager.database.lotto.AlgorithmTestLog
import com.hshim.lottomanager.database.lotto.Simulation
import com.hshim.lottomanager.database.lotto.repository.AlgorithmTestLogRepository
import com.hshim.lottomanager.database.lotto.repository.LottoRepository
import com.hshim.lottomanager.database.lotto.repository.SimulationRepository
import com.hshim.lottomanager.enums.lotto.NumberBuildAlgorithm
import com.hshim.lottomanager.exception.GlobalException
import com.hshim.lottomanager.model.lotto.LottoNumberBuildRequest
import com.hshim.lottomanager.model.simulation.SimulationResponse
import com.hshim.lottomanager.model.socket.SimulationEventModel
import com.hshim.lottomanager.service.lotto.LottoNumberQueryService
import com.hshim.lottomanager.setting.socket.SessionManager
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class SimulationCommandService(
    private val sessionManager: SessionManager,
    private val simulationRepository: SimulationRepository,
    private val lottoRepository: LottoRepository,
    private val algorithmTestLogRepository: AlgorithmTestLogRepository,
    private val lottoNumberQueryService: LottoNumberQueryService,
) {
    fun init(times: Int, algorithm: NumberBuildAlgorithm, cnt: Int): SimulationResponse {
        val lotto = lottoRepository.findByIdOrNull(times)
            ?.takeIf { it.isOpen }
            ?: throw GlobalException.NOT_FOUND_TIMES.exception
        val simulation = simulationRepository.save(Simulation(lotto = lotto, algorithm = algorithm, cnt = cnt))
        sessionManager.sendAll(SimulationEventModel.Initial(SimulationResponse(simulation)))
        return SimulationResponse(simulation)
    }

    fun process(simulation: Simulation, percentModel: SimulationEventModel.Percent) {
        val request = LottoNumberBuildRequest(
            algorithm = simulation.algorithm,
            cnt = simulation.cnt,
            detail = mutableMapOf("timesBefore" to simulation.lotto.times),
            progressingId = simulation.taskId,
        )

        val results = lottoNumberQueryService.numbersBuild(request).map {
            val testLog = algorithmTestLogRepository.saveAndFlush(
                AlgorithmTestLog(
                    lotto = simulation.lotto,
                    taskId = simulation.taskId,
                    numbers = it.numbers,
                    algorithm = request.algorithm,
                    request = request.detail?.toString(),
                )
            )
            percentModel.cnt++
            sessionManager.sendAll(percentModel)
            testLog
        }

        val resultModel = SimulationEventModel.Result(
            total = simulation.cnt,
            rank = results.groupBy { it.rank }.mapValues { it.value.count() },
            correctCnt = results.sumOf { it.correctCnt },
        )
        sessionManager.sendAll(resultModel)
    }
}