package com.hshim.lottomanager.service.account.oauth2

import com.hshim.lottomanager.model.account.oauth2.DiscordOauthAttribute
import com.hshim.lottomanager.database.account.repository.DiscordUserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomOAuth2UserService(
    private val discordUserRepository: DiscordUserRepository,
) : DefaultOAuth2UserService() {
    @Transactional
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        val attribute = getDiscordAttribute(oAuth2User)
        if (!discordUserRepository.existsByDiscordId(attribute.id)) {
            discordUserRepository.save(attribute.toEntity())
        }
        return oAuth2User
    }

    fun getDiscordAttribute(oAuth2User: OAuth2User) = DiscordOauthAttribute(oAuth2User.attributes)
    fun getDiscordAttribute(): DiscordOauthAttribute {
        val authentication = SecurityContextHolder.getContext().authentication.principal as OAuth2User
        return getDiscordAttribute(authentication)
    }
}