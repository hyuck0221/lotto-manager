package com.hshim.lottomanager.model.question

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.question.Question
import com.hshim.lottomanager.enums.question.QuestionType

class QuestionRequest(
    val type: QuestionType,
    val title: String,
    val content: String?,
    val isReplyAlert: Boolean,
) {
    fun toEntity(user: User) = Question(
        user = user,
        type = type,
        title = title,
        content = content,
        isReplyAlert = isReplyAlert,
    )

    fun updateTo(question: Question) {
        question.type = type
        question.title = title
        question.content = content
        question.isReplyAlert = isReplyAlert
    }
}