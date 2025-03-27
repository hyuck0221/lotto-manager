package com.hshim.lottomanager.api.simulation

import com.hshim.lottomanager.annotation.PublicEndpoint
import com.hshim.lottomanager.enums.lotto.NumberBuildAlgorithm
import com.hshim.lottomanager.model.lotto.LottoNumberBuildRequest
import com.hshim.lottomanager.service.lotto.algorithm.AlgorithmTestService
import com.hshim.lottomanager.service.simulation.SimulationCommandService
import io.autocrypt.sakarinblue.universe.util.CommonUtil.uuid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/lotto/simulation")
class SimulationController(
    private val simulationCommandService: SimulationCommandService,
) {
    @PublicEndpoint
    @PostMapping
    fun init(
        @RequestParam(required = true) times: Int,
        @RequestParam(required = true) algorithm: NumberBuildAlgorithm,
        @RequestParam(required = true) cnt: Int,
    ) = simulationCommandService.init(times, algorithm, cnt)
}