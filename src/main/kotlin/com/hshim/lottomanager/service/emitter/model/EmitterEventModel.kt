package com.hshim.lottomanager.service.emitter.model

import net.minidev.json.annotate.JsonIgnore

class EmitterEventModel {
    class ProcessingInfo(
        @JsonIgnore
        val id: String,
        var description: String,
        var executeCnt: Int = 1,
        var totalCnt: Int,
        var percent: Int = -1,
    ) : SSEBaseModel("processing_info")

    class ProgressingSuccessInfo : SSEBaseModel("progressing_success_info")
}