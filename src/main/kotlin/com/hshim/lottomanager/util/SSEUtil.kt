package com.hshim.lottomanager.util

import com.hshim.lottomanager.service.emitter.model.SSEBaseModel
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

object SSEUtil {
    fun SseEmitter.emitterSend(data: SSEBaseModel) = this.send(SseEmitter.event().name(data.eventName).data(data))
}