package com.hshim.lottomanager.model.game

import com.hshim.lottomanager.enums.game.Game

class GameResponse(
    val title: String,
    val description: String,
    val pagePath: String,
    val imagePath: String,
) {
    constructor(game: Game) : this(
        title = game.title,
        description = game.description,
        pagePath = game.pagePath,
        imagePath = game.iconPath,
    )
}