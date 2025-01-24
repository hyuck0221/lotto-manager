package com.hshim.lottomanager.database.account

import com.hshim.lottomanager.enums.account.UserType
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "github_user")
@DiscriminatorValue(value = "GITHUB")
class GithubUser(
    id: String,
    avatarUrl: String?,
    name: String,
    email: String?,

    @Column(nullable = true)
    var notificationEmail: String?,

    @Column(nullable = false)
    var createdAt: String,

    @Column(nullable = false)
    var updatedAt: String,
) : User(
    id = id,
    displayName = name,
    userType = UserType.GITHUB,
    profileUrl = avatarUrl,
    email = email,
)