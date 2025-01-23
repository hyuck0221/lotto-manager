package com.hshim.lottomanager.api.question

import com.hshim.lottomanager.model.question.QuestionRequest
import com.hshim.lottomanager.model.question.ReplyRequest
import com.hshim.lottomanager.service.question.QuestionCommandService
import com.hshim.lottomanager.service.question.QuestionQueryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/question")
class QuestionController(
    private val questionQueryService: QuestionQueryService,
    private val questionCommandService: QuestionCommandService,
) {
    @GetMapping("/list")
    fun getMyQuestions() = questionQueryService.getMyQuestions()

    @GetMapping("/{id}")
    fun getQuestion(
        @PathVariable id: String
    ) = questionQueryService.getQuestion(id)

    @PostMapping
    fun init(
        @RequestBody request: QuestionRequest,
    ) = questionCommandService.init(request)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody request: QuestionRequest,
    ) = questionCommandService.update(id, request)

    @DeleteMapping("/{id}")
    fun deleteQuestion(
        @PathVariable id: String,
    ) = questionCommandService.delete(id)

    @PostMapping("/reply/{id}")
    fun initReply(
        @PathVariable id: String,
        @RequestBody request: ReplyRequest,
    ) = questionCommandService.initReply(id, request)
}