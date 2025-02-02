package com.hshim.lottomanager.enums.game

enum class Game(
    val title: String,
    val description: String,
    val pagePath: String,
    val iconPath: String,
) {
    LOTTO(
        title = "가상로또",
        description = "포인트로 진행하는 가상로또 시뮬레이션 게임입니다.",
        pagePath = "/page/game/lotto",
        iconPath = "/icon/game/lotto.png",
    )
}