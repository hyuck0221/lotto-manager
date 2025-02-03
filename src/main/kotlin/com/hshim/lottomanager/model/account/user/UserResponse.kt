package com.hshim.lottomanager.model.account.user

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.enums.account.SendType
import com.hshim.lottomanager.enums.account.UserType

class UserResponse (
    val id: String,
    val displayName: String,
    val userType: UserType,
    val displayUserType: String,
    val profileUrl: String?,
    val email: String?,
    val sendType: SendType?,
    val displaySendType: String?,
) {
    constructor(user: User): this(
        id = user.id,
        displayName = user.displayName,
        userType = user.userType,
        displayUserType = user.userType.description,
        profileUrl = user.profileUrl,
        email = user.email,
        sendType = user.sendType,
        displaySendType = user.sendType?.description,
    )
}