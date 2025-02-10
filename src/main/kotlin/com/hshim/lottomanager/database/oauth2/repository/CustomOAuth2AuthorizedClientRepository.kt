package com.hshim.lottomanager.database.oauth2.repository

import com.hshim.lottomanager.database.oauth2.OAuth2AuthorizedClientEntity
import io.autocrypt.sakarinblue.universe.util.CommonUtil.uuid
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.OAuth2RefreshToken
import org.springframework.stereotype.Component

@Component
class CustomOAuth2AuthorizedClientRepository(
    private val clientRepository: Oauth2AuthorizedClientEntityRepository,
    private val clientRegistrationRepository: ClientRegistrationRepository,
) : OAuth2AuthorizedClientRepository {

    @Suppress("UNCHECKED_CAST")
    override fun <T : OAuth2AuthorizedClient> loadAuthorizedClient(
        clientRegistrationId: String,
        principal: Authentication,
        request: HttpServletRequest
    ): T? {
        val principalName = principal.name
        val entity = clientRepository.findByPrincipalNameAndClientRegistrationId(principalName, clientRegistrationId)
            ?: return null

        val clientRegistration = clientRegistrationRepository.findByRegistrationId(clientRegistrationId) ?: return null

        val scopes = entity.accessTokenScopes.split(",").map { it.trim() }.toSet()
        val accessToken = OAuth2AccessToken(
            OAuth2AccessToken.TokenType.BEARER,
            entity.accessToken,
            entity.accessTokenIssuedAt,
            entity.accessTokenExpiresAt,
            scopes
        )

        val refreshToken = entity.refreshToken?.let { OAuth2RefreshToken(it, entity.accessTokenIssuedAt) }
        return OAuth2AuthorizedClient(clientRegistration, principalName, accessToken, refreshToken) as T
    }

    override fun saveAuthorizedClient(
        authorizedClient: OAuth2AuthorizedClient,
        principal: Authentication,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        val principalName = principal.name
        val clientRegistrationId = authorizedClient.clientRegistration.registrationId
        val accessToken = authorizedClient.accessToken
        val refreshToken = authorizedClient.refreshToken

        val scopesString = accessToken.scopes.joinToString(separator = ",")

        val existingEntity =
            clientRepository.findByPrincipalNameAndClientRegistrationId(principalName, clientRegistrationId)

        val entity = OAuth2AuthorizedClientEntity(
            id = existingEntity?.id ?: uuid(),
            principalName = principalName,
            clientRegistrationId = clientRegistrationId,
            accessToken = accessToken.tokenValue,
            accessTokenIssuedAt = accessToken.issuedAt!!,
            accessTokenExpiresAt = accessToken.expiresAt!!,
            accessTokenScopes = scopesString,
            refreshToken = refreshToken?.tokenValue
        )
        clientRepository.save(entity)
    }

    override fun removeAuthorizedClient(
        clientRegistrationId: String,
        principal: Authentication,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        val principalName = principal.name
        clientRepository.deleteByPrincipalNameAndClientRegistrationId(principalName, clientRegistrationId)
    }
}