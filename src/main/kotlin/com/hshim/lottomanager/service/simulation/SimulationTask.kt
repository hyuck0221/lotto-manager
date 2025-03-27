package com.hshim.lottomanager.service.simulation

import com.hshim.lottomanager.database.lotto.repository.SimulationRepository
import com.hshim.lottomanager.model.socket.SimulationEventModel
import jakarta.transaction.Transactional
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SimulationTask(
    private val simulationRepository: SimulationRepository,
    private val simulationCommandService: SimulationCommandService,
) {
    @Scheduled(fixedDelay = 3 * 1000)
    @Transactional
    fun processSimulation() {
        val simulation = simulationRepository.findTopByInProcessFalseAndFinishFalse()
            ?: return

        try {
            val percentModel = SimulationEventModel.Percent(simulation.cnt, 0)
            simulationRepository.updateInProcessTrueById(simulation.id)
            simulationCommandService.process(simulation, percentModel)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            simulation.finish()
        }
    }
}