package com.hshim.lottomanager.database.account.email.repository

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.account.email.EmailVerify
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface EmailVerifyRepository : JpaRepository<EmailVerify, String> {
    fun findTopByEmailAndCodeAndCreateDateGreaterThanEqualAndVerifyFalseOrderByCreateDateDesc(
        email: String,
        code: String,
        createDate: LocalDateTime,
    ): EmailVerify?

    fun existsByUserAndEmailAndVerifyTrue(user: User, email: String): Boolean
}