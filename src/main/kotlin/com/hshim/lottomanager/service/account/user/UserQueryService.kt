package com.hshim.lottomanager.service.account.user

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.account.repository.UserRepository
import com.hshim.lottomanager.exception.GlobalException
import com.hshim.lottomanager.model.account.user.UserResponse
import org.springframework.data.domain.Pageable
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
        val user = userRepository.findByIdOrNull(id)
        when {
            user == null -> throw GlobalException.NOT_FOUND_USER.exception
            user.disable -> throw GlobalException.IS_DISABLED_USER.exception
        }
        return user!!
    }

    fun getUser() = getUser(SecurityContextHolder.getContext().authentication.name)

    fun search(name: String): List<User> {
        val me = getUser()
        val pageable = Pageable.ofSize(5)
        return userRepository.findAllByNameAndUserIdNot(name, me.id, pageable).content
    }

    fun getInfo(id: String): UserResponse {
        val user = getUser(id)
        return UserResponse(user)
    }
}