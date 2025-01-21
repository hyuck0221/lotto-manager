package com.hshim.lottomanager.model.question

import com.hshim.lottomanager.database.question.Question
import com.hshim.lottomanager.enums.question.QuestionType
import com.hshim.lottomanager.model.account.user.UserResponse

class QuestionResponse(
    val id: String,
    val user: UserResponse,
    val type: QuestionType,
    val displayType: String,
    val title: String,
    val content: String?,
    val isReplyAlert: Boolean,
    val reply: String?,
) {
    constructor(question: Question) : this(
        id = question.id,
        user = UserResponse(question.user),
        type = question.type,
        displayType = question.type.description,
        title = question.title,
        content = question.content,
        isReplyAlert = question.isReplyAlert,
        reply = question.reply,
    )
}