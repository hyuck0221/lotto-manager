package com.hshim.lottomanager.model.socket

import com.hshim.lottomanager.model.simulation.SimulationResponse

class SimulationEventModel {
    class Initial(
        val processingSimulation: SimulationResponse?,
    ) : BaseEventModel("simulation_initial")

    class Percent(
        val total: Int = 0,
        var cnt: Int = 0,
    ) : BaseEventModel("simulation_percent")

    class Result(
        val total: Int,
        val rank: Map<Int, Int>,
        val correctCnt: Int,
    ) : BaseEventModel("simulation_result")
}