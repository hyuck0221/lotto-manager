package com.hshim.lottomanager.model.account.oauth2

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.enums.account.UserType

abstract class OauthAttribute(
    val userType: UserType,
    val id: String,
    val displayName: String,
    val profileUrl: String?,
    val email: String?,
) {
    fun toEntity() = User(
        id = id,
        userType = userType,
        displayName = displayName,
        profileUrl = profileUrl,
        email = email,
    )

    open fun updateTo(user: User) {
        user.displayName = displayName
        user.profileUrl = profileUrl
        user.email = email
    }
}