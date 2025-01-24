package com.hshim.lottomanager.model.account.oauth2

import com.hshim.lottomanager.enums.account.UserType

class GoogleOauthAttribute(
    email: String?,
    val sub: String,
    val name: String,
    val givenName: String,
    val familyName: String?,
    val picture: String?,
    val emailVerified: Boolean,
) : OauthAttribute(
    userType = UserType.GOOGLE,
    id = sub,
    displayName = name,
    profileUrl = picture,
    email = email,
) {
    constructor(attributeMap: Map<String, Any>) : this(
        sub = attributeMap["sub"] as String,
        name = attributeMap["name"] as String,
        givenName = attributeMap["given_name"] as String,
        familyName = attributeMap["family_name"] as String?,
        picture = attributeMap["picture"] as String?,
        email = attributeMap["email"] as String?,
        emailVerified = attributeMap["email_verified"] as Boolean,
    )
}