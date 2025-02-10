package com.hshim.lottomanager.database.oauth2.repository

import com.hshim.lottomanager.database.oauth2.OAuth2AuthorizedClientEntity
import org.springframework.data.repository.CrudRepository

interface Oauth2AuthorizedClientEntityRepository : CrudRepository<OAuth2AuthorizedClientEntity, String> {
    fun findByPrincipalNameAndClientRegistrationId(
        principalName: String,
        clientRegistrationId: String,
    ): OAuth2AuthorizedClientEntity?

    fun deleteByPrincipalNameAndClientRegistrationId(
        principalName: String,
        clientRegistrationId: String,
    )
}