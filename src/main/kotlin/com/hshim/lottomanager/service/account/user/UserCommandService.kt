package com.hshim.lottomanager.service.account.user

import com.hshim.lottomanager.database.account.DiscordUser
import com.hshim.lottomanager.database.account.GoogleUser
import com.hshim.lottomanager.model.account.oauth2.DiscordOauthAttribute
import com.hshim.lottomanager.model.account.oauth2.GoogleOauthAttribute
import com.hshim.lottomanager.model.account.user.UserRequest
import com.hshim.lottomanager.model.account.user.UserResponse
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserCommandService(private val userQueryService: UserQueryService) {
    fun update(
        id: String,
        req: UserRequest,
    ): UserResponse {
        val user = userQueryService.getUser(id)
        req.updateTo(user)
        return UserResponse(user)
    }

    fun synchronization(
        id: String,
        authentication: OAuth2AuthenticationToken,
    ): UserResponse {
        val user = userQueryService.getUser(id)
        val attribute = authentication.principal.attributes
        when (user) {
            is DiscordUser -> DiscordOauthAttribute(attribute).updateTo(user)
            is GoogleUser -> GoogleOauthAttribute(attribute).updateTo(user)
        }
        return UserResponse(user)
    }
}