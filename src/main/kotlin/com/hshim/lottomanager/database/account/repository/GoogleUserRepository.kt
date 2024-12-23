package com.hshim.lottomanager.database.account.repository

import com.hshim.lottomanager.database.account.GoogleUser
import org.springframework.data.jpa.repository.JpaRepository

interface GoogleUserRepository : JpaRepository<GoogleUser, String> {
    fun existsBySub(sub: String): Boolean
}