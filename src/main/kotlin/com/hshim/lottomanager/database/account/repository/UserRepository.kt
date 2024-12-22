package com.hshim.lottomanager.database.account.repository

import com.hshim.lottomanager.database.account.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, String> {
}