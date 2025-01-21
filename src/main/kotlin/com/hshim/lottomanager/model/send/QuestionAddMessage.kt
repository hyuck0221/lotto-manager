package com.hshim.lottomanager.model.send

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.question.Question

class QuestionAddMessage(
    title: String,
    html: String,
) : SendModel(title, html) {
    constructor(
        user: User,
        question: Question,
    ) : this(
        title = "${user.displayName}님의 문의가 등록되었습니다.",
        html = """
            <h2>${question.type.description} | ${question.title}</h2>
            <p>답변 시 알림 ${if (question.isReplyAlert) "O" else "X"}</p>
            <p>${question.content}</p>
        """.trimIndent()
    )
}