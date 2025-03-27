package com.hshim.lottomanager.model.simulation

import com.hshim.lottomanager.database.lotto.Simulation
import com.hshim.lottomanager.enums.lotto.NumberBuildAlgorithm

class SimulationResponse(
    val id: String,
    val taskId: String,
    val algorithm: NumberBuildAlgorithm,
    val cnt: Int,
) {
    constructor(simulation: Simulation) : this(
        id = simulation.id,
        taskId = simulation.taskId,
        algorithm = simulation.algorithm,
        cnt = simulation.cnt,
    )
}