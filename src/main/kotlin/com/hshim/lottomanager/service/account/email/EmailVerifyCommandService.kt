package com.hshim.lottomanager.service.account.email

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.account.email.EmailVerify
import com.hshim.lottomanager.database.account.email.repository.EmailVerifyRepository
import com.hshim.lottomanager.exception.GlobalException
import com.hshim.lottomanager.model.send.EmailVerifyMessage
import com.hshim.lottomanager.service.send.SendService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class EmailVerifyCommandService(
    private val emailVerifyRepository: EmailVerifyRepository,
    private val sendService: SendService,
) {
    fun verify(email: String, code: String) {
        emailVerifyRepository.findTopByEmailAndCodeAndCreateDateGreaterThanEqualAndVerifyFalseOrderByCreateDateDesc(
            email = email,
            code = code,
            createDate = LocalDateTime.now().minusMinutes(3),
        )
            ?.apply { this.verify = true }
            ?: throw GlobalException.NOT_VERIFY_EMAIL.exception
    }

    fun verifyEmail(user: User, email: String?) {
        if (email == null) return
        if (!emailVerifyRepository.existsByUserAndEmailAndVerifyTrue(user, email))
            throw GlobalException.NOT_VERIFY_EMAIL.exception
    }

    fun buildCode(user: User, email: String) {
        val emailVerify = emailVerifyRepository.save(EmailVerify(user, email))
        sendService.sendEmailAsync(email, EmailVerifyMessage(user, emailVerify), user)
    }

    fun deleteVerify(id: String) {
        emailVerifyRepository.deleteById(id)
    }
}