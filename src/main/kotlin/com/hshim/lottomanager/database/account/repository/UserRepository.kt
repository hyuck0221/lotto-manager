package com.hshim.lottomanager.database.account.repository

import com.hshim.lottomanager.database.account.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, String> {
    @Query(
        """
            select u from User u 
            where u.id != :userId 
            and u.displayName like concat('%', :name, '%') 
        """
    )
    fun findAllByNameAndUserIdNot(
        name: String,
        userId: String,
        pageable: Pageable,
    ): Page<User>
}