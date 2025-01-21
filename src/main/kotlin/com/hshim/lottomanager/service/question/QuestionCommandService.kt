package com.hshim.lottomanager.service.question

import com.hshim.lottomanager.database.question.repository.QuestionRepository
import com.hshim.lottomanager.exception.GlobalException
import com.hshim.lottomanager.model.question.QuestionRequest
import com.hshim.lottomanager.model.question.QuestionResponse
import com.hshim.lottomanager.model.send.QuestionAddMessage
import com.hshim.lottomanager.model.send.SendModel
import com.hshim.lottomanager.service.account.user.UserQueryService
import com.hshim.lottomanager.service.send.SendService
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class QuestionCommandService(
    private val questionRepository: QuestionRepository,
    private val userQueryService: UserQueryService,
    private val sendService: SendService,
    @Value("\${spring.mail.username}")
    private val email: String,
) {
    fun init(request: QuestionRequest): QuestionResponse {
        val user = userQueryService.getUser()
        val question = questionRepository.save(request.toEntity(user))
        sendService.sendEmail(email, QuestionAddMessage(user, question))
        return QuestionResponse(question)
    }

    fun update(
        id: String,
        request: QuestionRequest
    ): QuestionResponse {
        val question = questionRepository.findByIdOrNull(id)
            ?: throw GlobalException.NOT_FOUND_QUESTION.exception
        request.updateTo(question)
        return QuestionResponse(question)
    }

    fun delete(id: String) = questionRepository.deleteById(id)
}