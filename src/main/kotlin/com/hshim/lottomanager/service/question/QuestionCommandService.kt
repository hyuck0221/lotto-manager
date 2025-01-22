package com.hshim.lottomanager.service.question

import com.hshim.lottomanager.database.question.repository.QuestionRepository
import com.hshim.lottomanager.exception.GlobalException
import com.hshim.lottomanager.model.question.QuestionRequest
import com.hshim.lottomanager.model.question.QuestionResponse
import com.hshim.lottomanager.model.send.QuestionAddMessage
import com.hshim.lottomanager.service.account.admin.AdminQueryService
import com.hshim.lottomanager.service.account.user.UserQueryService
import com.hshim.lottomanager.service.send.SendService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class QuestionCommandService(
    private val questionRepository: QuestionRepository,
    private val userQueryService: UserQueryService,
    private val sendService: SendService,
    private val adminQueryService: AdminQueryService,
) {
    fun init(request: QuestionRequest): QuestionResponse {
        val user = userQueryService.getUser()
        val question = questionRepository.save(request.toEntity(user))
        val emails = adminQueryService.getEmails()
        emails.forEach { sendService.sendEmail(it, QuestionAddMessage(user, question)) }
        return QuestionResponse(question)
    }

    fun update(
        id: String,
        request: QuestionRequest,
    ): QuestionResponse {
        val question = questionRepository.findByIdOrNull(id)
            ?: throw GlobalException.NOT_FOUND_QUESTION.exception
        request.updateTo(question)
        return QuestionResponse(question)
    }

    fun delete(id: String) = questionRepository.deleteById(id)
}