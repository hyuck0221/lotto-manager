package com.hshim.lottomanager.service.send

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.enums.account.SendType
import com.hshim.lottomanager.model.send.SendModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class SendService(
    private val mailSender: JavaMailSender,
    @Value("\${spring.mail.username}")
    private val mail: String,
) {
    fun send(user: User, message: SendModel) {
        when (user.sendType) {
            SendType.EMAIL -> sendEmail(user, message)
            else -> return
        }
    }

    private fun sendEmail(user: User, message: SendModel) {
        if (user.email == null) return
        val mimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage, true, "UTF-8")

        helper.setTo(user.email!!)
        helper.setSubject(message.title)
        helper.setText(message.html, true)
        helper.setFrom(mail)

        mailSender.send(mimeMessage)
    }
}