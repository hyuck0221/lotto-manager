package com.hshim.lottomanager.database.send.repository

import com.hshim.lottomanager.database.send.EmailLog
import org.springframework.data.jpa.repository.JpaRepository

interface EmailLogRepository : JpaRepository<EmailLog, Int> {
}