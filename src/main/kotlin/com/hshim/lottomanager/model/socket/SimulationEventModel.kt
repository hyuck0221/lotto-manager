package com.hshim.lottomanager.model.socket

import com.hshim.realtimeanonymouschat.database.entity.chat.Chat
import io.autocrypt.sakarinblue.universe.util.DateUtil.dateToString

class SimulationEventModel {
    class InitialInfo(
        val processingSimulation: SimulationInfo,
        val
    )

    class SimulationInfo(
        val id: String,
        val taskId: String,
        val percent: BaseEventModel,
    ): BaseEventModel("simulation_info")

    class SimulationPercentInfo(
        val total: Int,
        val cnt: Int,
    ): BaseEventModel("simulation_percent_info")

    class SessionInfo(
        val id: String,
        val beforeChats: List<BeforeChat>,
    ): BaseEventModel("session_info") {
        class BeforeChat(
            val content: String,
            val sessionId: String,
            val createDate: String,
        ) {
            constructor(chat: Chat): this (
                chat.content,
                chat.sessionId,
                chat.createDate.dateToString(),
            )
        }
    }
}