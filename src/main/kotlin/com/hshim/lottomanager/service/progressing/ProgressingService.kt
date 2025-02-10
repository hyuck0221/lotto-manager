package com.hshim.lottomanager.service.progressing

import com.hshim.lottomanager.service.emitter.EmitterManager
import com.hshim.lottomanager.service.emitter.model.EmitterEventModel
import com.hshim.lottomanager.util.SSEUtil.emitterSend
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Service
class ProgressingService(
    private val emitterManager: EmitterManager,
) {
    fun connect(id: String): SseEmitter {
        val emitter = SseEmitter(60 * 1000)
        emitter.onCompletion { emitterManager.progressingEmitterMap.remove(id) }
        emitter.onTimeout { emitter.complete() }
        emitter.onError { emitter.complete() }
        emitter.send("connect")
        emitterManager.addProgressingEmitter(id, emitter)
        return emitter
    }

    fun percentSend(model: EmitterEventModel.ProcessingInfo) {
        emitterManager.progressingEmitterMap[model.id]
            ?.apply { this.emitterSend(model) }
            ?: return
    }

    fun complete(model: EmitterEventModel.ProcessingInfo) {
        emitterManager.progressingEmitterMap[model.id]
            ?.apply {
                this.emitterSend(model.apply { this.percent = 100 })
                this.emitterSend(EmitterEventModel.ProgressingSuccessInfo())
                this.complete()
            }
    }
}