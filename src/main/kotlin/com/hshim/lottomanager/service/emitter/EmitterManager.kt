package com.hshim.lottomanager.service.emitter

import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Component
class EmitterManager {
    val progressingEmitterMap: MutableMap<String, SseEmitter> = mutableMapOf()
    fun addProgressingEmitter(id: String, emitter: SseEmitter) {
        progressingEmitterMap[id] = emitter
    }
}