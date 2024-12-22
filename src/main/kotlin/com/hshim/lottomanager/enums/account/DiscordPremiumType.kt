package com.hshim.lottomanager.enums.account

enum class DiscordPremiumType(
    val value: Int,
    val description: String,
) {
    NONE(0, "None"),
    NITRO_CLASSIC(1, "Nitro Classic"),
    NITRO(2, "Nitro"),
    NITRO_BASIC(3, "Nitro Basic"),
    ;

    companion object {
        fun getByValue(value: Int) = entries.first { it.value == value }
    }
}