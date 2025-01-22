package com.hshim.lottomanager.service.account.admin

import com.hshim.lottomanager.database.account.Admin
import com.hshim.lottomanager.database.account.repository.AdminRepository
import com.hshim.lottomanager.exception.GlobalException
import com.hshim.lottomanager.model.account.user.UserResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AdminQueryService(private val adminRepository: AdminRepository) {
    fun getAdminByUserId(userId: String): Admin {
        return adminRepository.findByUserId(userId)
            ?: throw GlobalException.IS_NOT_ADMIN.exception
    }

    fun getAdmin() = getAdminByUserId(SecurityContextHolder.getContext().authentication.name)
    fun getEmails(): List<String> = adminRepository.findAllEmail()

    fun search(name: String?): List<UserResponse> {
        val me = getAdmin()
        return adminRepository.findAllByNameAndUserIdNot(
            id = me.id,
            name = name ?: "",
        ).map { UserResponse(it.user) }
    }
}