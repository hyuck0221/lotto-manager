package com.hshim.lottomanager.database.account.repository

import com.hshim.lottomanager.database.account.Admin
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AdminRepository : JpaRepository<Admin, String> {
    fun findByUserId(userId: String): Admin?
    @Query(
        """
            select a from Admin a 
            inner join fetch a.user  
            where a.id != :id 
            and a.user.displayName like concat('%', :name, '%') 
        """
    )
    fun findAllByNameAndUserIdNot(
        id: String,
        name: String,
    ): List<Admin>

    @Query(
        """
            select u.email from Admin a 
            inner join User u on u = a.user
        """
    )
    fun findAllEmail(): List<String>
    fun deleteByUserId(userId: String)
}