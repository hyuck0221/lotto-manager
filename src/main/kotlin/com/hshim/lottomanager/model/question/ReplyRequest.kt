package com.hshim.lottomanager.model.question

import com.hshim.lottomanager.database.question.Question

class ReplyRequest(
    val content: String,
) {
    fun updateTo(question: Question) {
        question.reply = content
    }
}