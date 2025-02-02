package com.hshim.lottomanager.model.game.point

import com.hshim.lottomanager.database.game.Point

class PointResponse (
    val point: Int,
) {
    constructor(point: Point?): this (
        point = point?.amount ?: 0
    )
}