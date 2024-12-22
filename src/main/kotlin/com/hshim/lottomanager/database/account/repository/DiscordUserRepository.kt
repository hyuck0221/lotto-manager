package com.hshim.lottomanager.database.account.repository

import com.hshim.lottomanager.database.account.DiscordUser
import org.springframework.data.jpa.repository.JpaRepository

interface DiscordUserRepository : JpaRepository<DiscordUser, String> {
    fun existsByDiscordId(discordId: String): Boolean
}