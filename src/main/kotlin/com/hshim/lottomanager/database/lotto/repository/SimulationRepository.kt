package com.hshim.lottomanager.database.lotto.repository

import com.hshim.lottomanager.database.lotto.Simulation
import jakarta.transaction.Transactional
import jakarta.transaction.Transactional.TxType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface SimulationRepository : JpaRepository<Simulation, String> {
    fun findTopByInProcessTrueAndFinishFalse(): Simulation?
    fun findTopByInProcessFalseAndFinishFalse(): Simulation?

    @Modifying
    @Transactional(value = TxType.REQUIRES_NEW)
    @Query("update Simulation set inProcess = true where id = :id")
    fun updateInProcessTrueById(id: String)
}