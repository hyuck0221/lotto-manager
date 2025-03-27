package com.hshim.lottomanager.setting.socket

import com.hshim.lottomanager.database.lotto.repository.SimulationRepository
import com.hshim.lottomanager.model.simulation.SimulationResponse
import com.hshim.lottomanager.model.socket.BaseEventModel
import com.hshim.lottomanager.model.socket.SimulationEventModel
import io.autocrypt.sakarinblue.universe.util.CommonUtil.convertObject2JsonString
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession

@Component
class SessionManager(private val simulationRepository: SimulationRepository) {
    val sessions = mutableSetOf<WebSocketSession>()

    fun addSession(session: WebSocketSession) {
        sessions.add(session)
        val simulation = simulationRepository.findTopByInProcessTrueAndFinishFalse()

        if (simulation == null) {
            session.send(SimulationEventModel.Initial(null))
            return
        }

        session.send(SimulationEventModel.Initial(SimulationResponse(simulation)))
    }

    fun removeSession(session: WebSocketSession) {
        sessions.remove(session)
    }

    fun WebSocketSession.send(event: BaseEventModel) {
        if (this.isOpen) this.sendMessage(TextMessage(convertObject2JsonString(event)))
    }

    fun sendAll(event: BaseEventModel) {
        sessions.forEach { it.send(event) }
    }
}