package com.hshim.lottomanager.model.account.user

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.enums.account.SendType

class UserRequest (
    val displayName: String,
    val profileUrl: String?,
    val email: String?,
    val sendType: SendType?,
) {
    fun updateTo(user: User) {
        user.displayName = displayName
        user.profileUrl = profileUrl
        user.email = email
        user.sendType = sendType
    }
}