package com.hshim.lottomanager.service.account.admin

import com.hshim.lottomanager.database.account.Admin
import com.hshim.lottomanager.database.account.repository.AdminRepository
import com.hshim.lottomanager.exception.GlobalException
import com.hshim.lottomanager.service.account.user.UserQueryService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AdminCommandService(
    private val adminRepository: AdminRepository,
    private val userQueryService: UserQueryService,
) {
    fun init(id: String) {
        val user = userQueryService.getUser(id)
        adminRepository.save(Admin(user = user))
    }

    fun delete(id: String) {
        val me = userQueryService.getUser()
        if (id == me.id) throw GlobalException.CAN_NOT_DELETE_MY_ADMIN_ROLE.exception
        adminRepository.deleteByUserId(id)
    }
}