package com.hshim.lottomanager.model.account.user

import com.hshim.lottomanager.database.account.User

class UserRequest (
    val displayName: String,
    val profileUrl: String?,
) {
    fun updateTo(user: User) {
        user.displayName = displayName
        user.profileUrl = profileUrl
    }
}