package com.hshim.lottomanager.enums.game

enum class Game(
    val title: String,
    val description: String,
    val pagePath: String,
    val iconPath: String,
) {
    LOTTO(
        title = "가상로또",
        description = "포인트로 진행하는 가상로또 게임입니다.",
        pagePath = "/page/game/lotto",
        iconPath = "/icon/game/lotto.png",
    ),

    SPEEDO500(
        title = "스피또 500",
        description = "같은 금액이 3개 나오면 당첨되는 가상 스피또 게임입니다.",
        pagePath = "/page/game/speedo/500",
        iconPath = "/icon/game/speedo/500.png",
    ),

    SPEEDO1000(
        title = "스피또 1000",
        description = "행운숫자와 나의 숫자가 일치하면 당첨되는 가상 스피또 게임입니다.",
        pagePath = "/page/game/speedo/1000",
        iconPath = "/icon/game/speedo/1000.png",
    ),

    SPEEDO2000(
        title = "스피또 2000",
        description = "행운그림 2개가 모두 일치하면 당첨되는 가상 스피또 게임입니다.",
        pagePath = "/page/game/speedo/2000",
        iconPath = "/icon/game/speedo/2000.png",
    ),
}