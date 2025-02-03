package com.hshim.lottomanager.service.account.user

import com.hshim.lottomanager.model.account.user.UserRequest
import com.hshim.lottomanager.model.account.user.UserResponse
import com.hshim.lottomanager.service.account.email.EmailVerifyCommandService
import com.hshim.lottomanager.service.account.oauth2.wrapper.OauthAttributeWrapper
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserCommandService(
    private val userQueryService: UserQueryService,
    private val emailVerifyCommandService: EmailVerifyCommandService,
    private val oauthAttributeWrappers: List<OauthAttributeWrapper>,
) {
    fun update(
        id: String,
        req: UserRequest,
    ): UserResponse {
        val user = userQueryService.getUser(id)
        emailVerifyCommandService.verifyEmail(user, req.email)
        req.updateTo(user)
        return UserResponse(user)
    }

    fun synchronization(
        id: String,
        authentication: OAuth2AuthenticationToken,
    ): UserResponse {
        val user = userQueryService.getUser(id)
        val attribute = authentication.principal.attributes
        oauthAttributeWrappers.find { it.support(user.userType) }
            ?.apply { this.getAttribute(attribute).updateTo(user) }
        return UserResponse(user)
    }
}