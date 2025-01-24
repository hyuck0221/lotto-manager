package com.hshim.lottomanager.database.account.repository

import com.hshim.lottomanager.database.account.KakaoUser
import org.springframework.data.jpa.repository.JpaRepository

interface KakaoUserRepository : JpaRepository<KakaoUser, String> {
}