package com.hshim.lottomanager.database.question.repository

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.question.Question
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionRepository : JpaRepository<Question, String> {
    fun findAllByUserOrderByCreateDateDesc(user: User): List<Question>
    fun findByReplyId(replyId: String): Question?
}