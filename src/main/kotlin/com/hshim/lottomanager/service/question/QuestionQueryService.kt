package com.hshim.lottomanager.service.question

import com.hshim.lottomanager.database.question.repository.QuestionRepository
import com.hshim.lottomanager.exception.GlobalException
import com.hshim.lottomanager.model.question.QuestionResponse
import com.hshim.lottomanager.service.account.user.UserQueryService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QuestionQueryService(
    private val questionRepository: QuestionRepository,
    private val userQueryService: UserQueryService,
) {
    fun getMyQuestions(): List<QuestionResponse> {
        val user = userQueryService.getUser()
        return questionRepository.findAllByUserOrderByCreateDateDesc(user).map { QuestionResponse(it) }
    }
    fun getQuestion(id: String): QuestionResponse {
        return questionRepository.findByIdOrNull(id)
            ?.let { QuestionResponse(it) }
            ?: throw GlobalException.NOT_FOUND_QUESTION.exception
    }
}