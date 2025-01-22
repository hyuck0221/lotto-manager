package com.hshim.lottomanager.service.send

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.send.EmailLog
import com.hshim.lottomanager.database.send.repository.EmailLogRepository
import com.hshim.lottomanager.enums.account.SendType
import com.hshim.lottomanager.model.send.SendModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class SendService(
    private val mailSender: JavaMailSender,
    @Value("\${spring.mail.username}")
    private val mail: String,
    private val emailLogRepository: EmailLogRepository,
) {
    fun send(user: User, message: SendModel) {
        when (user.sendType) {
            SendType.EMAIL -> sendEmailAsync(user.email!!, message, user)
            else -> return
        }
    }

    @Async
    fun sendEmailAsync(email: String, message: SendModel, user: User? = null) {
        val mimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage, true, "UTF-8")

        helper.setTo(email)
        helper.setSubject(message.title)
        helper.setText(message.html, true)
        helper.setFrom(mail)

        mailSender.send(mimeMessage)
        emailLogRepository.save(EmailLog(user = user, email = user?.email ?: email))
    }
}