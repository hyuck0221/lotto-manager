package com.hshim.lottomanager.database.account

import com.hshim.lottomanager.enums.account.UserType
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "google_user")
@DiscriminatorValue(value = "GOOGLE")
class GoogleUser(
    profileUrl: String?,
    email: String?,

    @Column(nullable = false, columnDefinition = "varchar(100)")
    val sub: String,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var givenName: String,

    @Column(nullable = false)
    var familyName: String,

    @Column(nullable = false)
    var emailVerified: Boolean,
) : User(
    displayName = name,
    userType = UserType.GOOGLE,
    profileUrl = profileUrl,
    email = email,
)