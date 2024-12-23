package com.hshim.lottomanager.database.account

import com.hshim.lottomanager.enums.account.DiscordPremiumType
import com.hshim.lottomanager.enums.account.UserType
import jakarta.persistence.*

@Entity
@Table(name = "discord_user")
@DiscriminatorValue(value = "DISCORD")
class DiscordUser(
    email: String?,

    @Column(nullable = false, columnDefinition = "varchar(100)")
    val discordId: String,

    @Column(nullable = false)
    var username: String,

    @Column(nullable = false)
    var globalName: String,

    @Column(nullable = true)
    var avatar: String?,

    @Column(nullable = true)
    var banner: String?,

    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    var premiumType: DiscordPremiumType,
) : User(
    id = discordId,
    displayName = globalName,
    userType = UserType.DISCORD,
    profileUrl = avatar?.let { "https://cdn.discordapp.com/avatars/$discordId/$it" },
    email = email,
)