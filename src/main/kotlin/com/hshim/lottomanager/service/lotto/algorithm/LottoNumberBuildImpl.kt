package com.hshim.lottomanager.service.lotto.algorithm

import com.hshim.lottomanager.service.emitter.model.EmitterEventModel
import com.hshim.lottomanager.service.progressing.ProgressingService

abstract class LottoNumberBuildImpl(
    private val progressingService: ProgressingService,
) : LottoNumberBuildWrapper {
    fun List<Int>.numberSort() = this.sorted()
    fun EmitterEventModel.ProcessingInfo.updatePercent(total: Int, finish: Int) {
        val percent = ((finish / total.toDouble()) * 100).toInt()
        if (this.percent == percent) return

        this.percent = percent
        progressingService.percentSend(this)
    }

    fun EmitterEventModel.ProcessingInfo.complete() = progressingService.complete(this)
}