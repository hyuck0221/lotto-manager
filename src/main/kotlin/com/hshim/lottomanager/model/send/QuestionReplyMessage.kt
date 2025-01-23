package com.hshim.lottomanager.model.send

import com.hshim.lottomanager.database.question.Question

class QuestionReplyMessage(
    title: String,
    html: String,
) : SendModel(title, html) {
    constructor(question: Question) : this(
        title = "문의 답변이 도착했습니다.",
        html = """
            <h2>${question.type.description} | ${question.title}</h2>
            <p>답변 시 알림 ${if (question.isReplyAlert) "O" else "X"}</p>
            <p>${question.content ?: ""}</p>
            <h2>답변</h2>
            <p>${question.reply ?: ""}</p>
            <br>
            <a href="https://lotto.hshim-universe.com/page/question/detail?id=${question.id}">웹에서 보기</a>
        """.trimIndent()
    )
}