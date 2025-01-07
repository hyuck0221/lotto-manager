package com.hshim.lottomanager.database.lotto.repository

import com.hshim.lottomanager.database.lotto.AlgorithmTestLog
import org.springframework.data.jpa.repository.JpaRepository

interface AlgorithmTestLogRepository : JpaRepository<AlgorithmTestLog, String> {
}