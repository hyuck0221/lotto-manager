package com.hshim.lottomanager.model.account.oauth2

import com.hshim.lottomanager.database.account.GithubUser

class GithubOauthAttribute(
    val login: String,
    val id: Int,
    val nodeId: String,
    val avatarUrl: String?,
    val name: String,
    val createdAt: String,
    val updatedAt: String,
    val email: String?,
    val notificationEmail: String?,
) {
    constructor(attributeMap: Map<String, Any>) : this(
        login = attributeMap["login"] as String,
        id = attributeMap["id"] as Int,
        nodeId = attributeMap["node_id"] as String,
        avatarUrl = attributeMap["avatar_url"] as String?,
        name = attributeMap["name"] as String,
        createdAt = attributeMap["created_at"] as String,
        updatedAt = attributeMap["updated_at"] as String,
        email = attributeMap["email"] as String?,
        notificationEmail = attributeMap["notification_email"] as String?,
    )

    fun toEntity() = GithubUser(
        id = id.toString(),
        avatarUrl = avatarUrl,
        name = name,
        email = email,
        notificationEmail = notificationEmail,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    fun updateTo(githubUser: GithubUser) {
        githubUser.displayName = name
        githubUser.profileUrl = avatarUrl
        githubUser.email = email
        githubUser.notificationEmail = notificationEmail
        githubUser.updatedAt = updatedAt
    }
}