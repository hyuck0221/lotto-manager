package com.hshim.lottomanager.service.account.oauth2

import com.hshim.lottomanager.database.account.repository.DiscordUserRepository
import com.hshim.lottomanager.database.account.repository.GoogleUserRepository
import com.hshim.lottomanager.database.account.repository.UserRepository
import com.hshim.lottomanager.enums.account.UserType
import com.hshim.lottomanager.exception.GlobalException
import com.hshim.lottomanager.model.account.oauth2.DiscordOauthAttribute
import com.hshim.lottomanager.model.account.oauth2.GoogleOauthAttribute
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomOAuth2UserService(
    private val discordUserRepository: DiscordUserRepository,
    private val googleUserRepository: GoogleUserRepository,
    private val userRepository: UserRepository,
) : DefaultOAuth2UserService() {
    @Transactional
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        if (!userRepository.existsById(oAuth2User.name)) {
            val userType = UserType.findByRegistrationId(userRequest.clientRegistration.registrationId)
            when (userType) {
                UserType.DISCORD -> discordUserRepository.save(DiscordOauthAttribute(oAuth2User.attributes).toEntity())
                UserType.GOOGLE -> googleUserRepository.save(GoogleOauthAttribute(oAuth2User.attributes).toEntity())
                else -> throw GlobalException.NOT_SUPPORT_OAUTH.exception
            }
        }
        return oAuth2User
    }
}