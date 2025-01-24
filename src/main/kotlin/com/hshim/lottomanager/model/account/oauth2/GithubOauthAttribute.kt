package com.hshim.lottomanager.model.account.oauth2

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.enums.account.UserType

class GithubOauthAttribute(
    id: Int,
    email: String?,
    val login: String,
    val nodeId: String,
    val avatarUrl: String?,
    val name: String,
    val createdAt: String,
    val updatedAt: String,
    val notificationEmail: String?,
) : OauthAttribute(
    userType = UserType.GITHUB,
    id = id.toString(),
    displayName = name,
    profileUrl = avatarUrl,
    email = email,
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

    override fun updateTo(user: User) {
        user.displayName = displayName
        user.profileUrl = profileUrl
        user.email = email
    }
}