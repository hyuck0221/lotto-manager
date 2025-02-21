package com.hshim.lottomanager.model.game

abstract class SpeedoResponse(
    val rank: Int,
    val point: Int,
)

class Speedo500Response(
    val results: List<String>,
    rank: Int,
    point: Int,
): SpeedoResponse(rank, point)