package com.hshim.lottomanager.api.progressing

import com.hshim.lottomanager.service.progressing.ProgressingService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RestController
@RequestMapping("/api/progressing")
class ProgressingController(
    private val progressingService: ProgressingService,
) {
    @GetMapping("/{id}", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun connect(@PathVariable id: String): SseEmitter {
        return progressingService.connect(id)
    }
}