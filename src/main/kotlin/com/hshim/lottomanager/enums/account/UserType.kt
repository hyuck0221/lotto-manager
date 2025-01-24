package com.hshim.lottomanager.enums.account

enum class UserType(
    val description: String,
    val registrationId: String,
) {
    DISCORD("디스코드", "discord"),
    GOOGLE("구글", "google"),
    GITHUB("깃허브", "github"),
    KAKAO("카카오", "kakao"),
    ;

    companion object {
        fun findByRegistrationId(registrationId: String) = UserType.entries.find { it.registrationId == registrationId }
    }
}