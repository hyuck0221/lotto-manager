package com.hshim.lottomanager.aspect.admin

import com.hshim.lottomanager.annotation.admin.Admin
import com.hshim.lottomanager.service.account.admin.AdminQueryService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class RoleCheckAspect(private val adminQueryService: AdminQueryService) {

    @Around("@annotation(com.hshim.lottomanager.annotation.admin.Admin)")
    fun aroundJoinPoint(
        joinPoint: ProceedingJoinPoint,
        admin: Admin
    ): Any {
        adminQueryService.getAdmin()
        return joinPoint.proceed()
    }
}