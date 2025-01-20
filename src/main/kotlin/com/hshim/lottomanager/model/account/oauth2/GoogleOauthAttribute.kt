package com.hshim.lottomanager.model.account.oauth2

import com.hshim.lottomanager.database.account.GoogleUser

class GoogleOauthAttribute(
    val sub: String,
    val name: String,
    val givenName: String,
    val familyName: String?,
    val picture: String?,
    val email: String?,
    val emailVerified: Boolean,
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

    fun toEntity() = GoogleUser(
        sub = sub,
        name = name,
        givenName = givenName,
        familyName = familyName,
        profileUrl = picture,
        email = email,
        emailVerified = emailVerified
    )

    fun updateTo(googleUser: GoogleUser) {
        googleUser.name = name
        googleUser.displayName = name
        googleUser.givenName = givenName
        googleUser.familyName = familyName
        googleUser.profileUrl = picture
        googleUser.email = email
        googleUser.emailVerified = emailVerified
    }
}