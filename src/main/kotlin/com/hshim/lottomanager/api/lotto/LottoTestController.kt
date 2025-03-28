package com.hshim.lottomanager.api.lotto

import com.hshim.lottomanager.annotation.PublicEndpoint
import com.hshim.lottomanager.model.lotto.LottoNumberBuildRequest
import com.hshim.lottomanager.service.lotto.algorithm.AlgorithmTestService
import io.autocrypt.sakarinblue.universe.util.CommonUtil.uuid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/lotto/test")
class LottoTestController(
    private val algorithmTestService: AlgorithmTestService
) {
    @PublicEndpoint
    @PostMapping("/algorithm")
    fun algorithmTest(
        @RequestParam(required = true) times: Int,
        @RequestBody request: LottoNumberBuildRequest
    ) = algorithmTestService.test(times, request, uuid())

    @PostMapping("/algorithms")
    fun allAlgorithmTest(
        @RequestParam(required = true) times: Int,
        @RequestParam(required = true) cnt: Int,
    ) = algorithmTestService.testAll(times, cnt)
}