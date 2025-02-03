package com.hshim.lottomanager.api.lotto

import com.hshim.lottomanager.model.lotto.LottoNumberBuildRequest
import com.hshim.lottomanager.model.lotto.LottoNumberRequest
import com.hshim.lottomanager.model.lotto.UrlDecodeRequest
import com.hshim.lottomanager.service.lotto.LottoNumberCommandService
import com.hshim.lottomanager.service.lotto.LottoNumberQueryService
import com.hshim.lottomanager.service.lotto.LottoQueryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/lotto")
class LottoController(
    private val lottoNumberQueryService: LottoNumberQueryService,
    private val lottoQueryService: LottoQueryService,
    private val lottoNumberCommandService: LottoNumberCommandService,
) {
    @PostMapping("/number")
    fun initLottoNumber(
        @RequestBody request: LottoNumberRequest
    ) = lottoNumberCommandService.init(request)

    @PostMapping("/numbers")
    fun initLottoNumbers(
        @RequestBody requests: List<LottoNumberRequest>
    ) = lottoNumberCommandService.init(requests)

    @GetMapping("/numbers")
    fun getMyNumbers() = lottoNumberQueryService.getNumbers()

    @DeleteMapping("/number/{id}")
    fun deleteLottoNumber(
        @PathVariable id: String,
    ) = lottoNumberCommandService.delete(id)

    @PostMapping("/url/decode")
    fun getRequests(
        @RequestBody request: UrlDecodeRequest,
    ) = lottoNumberQueryService.getRequests(request)

    @PostMapping("/numbers/build")
    fun numbersBuild(
        @RequestBody request: LottoNumberBuildRequest,
    ) = lottoNumberQueryService.numbersBuild(request)

    @GetMapping("/times/latest")
    fun timesLatest() = lottoQueryService.timesLatest()
}