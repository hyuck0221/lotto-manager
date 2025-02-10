package com.hshim.lottomanager.database.oauth2

import io.autocrypt.sakarinblue.universe.util.CommonUtil.uuid
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(
    name = "oauth2_authorized_client", uniqueConstraints = [
        UniqueConstraint(columnNames = ["principalName", "clientRegistrationId"])
    ]
)
data class OAuth2AuthorizedClientEntity(
    @Id
    @Column(nullable = false)
    val id: String = uuid(),

    @Column(nullable = false)
    val principalName: String,

    @Column(nullable = false)
    val clientRegistrationId: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val accessToken: String,

    @Column(nullable = false)
    val accessTokenIssuedAt: Instant,

    @Column(nullable = false)
    val accessTokenExpiresAt: Instant,

    @Column(nullable = false)
    val accessTokenScopes: String,

    @Column(columnDefinition = "TEXT")
    val refreshToken: String?
)