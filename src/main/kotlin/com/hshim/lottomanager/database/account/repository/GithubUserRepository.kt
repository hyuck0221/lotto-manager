package com.hshim.lottomanager.database.account.repository

import com.hshim.lottomanager.database.account.GithubUser
import org.springframework.data.jpa.repository.JpaRepository

interface GithubUserRepository : JpaRepository<GithubUser, String> {
}