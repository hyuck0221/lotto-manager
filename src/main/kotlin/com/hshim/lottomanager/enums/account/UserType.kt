package com.hshim.lottomanager.enums.account

enum class UserType(
    val description: String,
    val registrationId: String,
) {
    DISCORD("디스코드", "discord"),
    GOOGLE("구글", "google"),
    ;

    companion object {
        fun findByRegistrationId(registrationId: String) = UserType.entries.find { it.registrationId == registrationId }
    }
}