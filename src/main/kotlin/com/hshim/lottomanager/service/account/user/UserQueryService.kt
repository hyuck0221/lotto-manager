package com.hshim.lottomanager.service.account.user

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.account.repository.UserRepository
import com.hshim.lottomanager.exception.GlobalException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserQueryService(
    private val userRepository: UserRepository,
) {
    fun getUser(id: String): User {
        val user = userRepository.findByIdOrNull("115146569511046352279")
        when {
            user == null -> throw GlobalException.NOT_FOUND_USER.exception
            user.disable -> throw GlobalException.IS_DISABLED_USER.exception
        }
        return user!!
    }

    fun getUser() = getUser(SecurityContextHolder.getContext().authentication.name)
}