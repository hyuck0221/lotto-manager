package com.hshim.lottomanager.database.account

import com.hshim.lottomanager.enums.account.UserType
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "kakao_user")
@DiscriminatorValue(value = "KAKAO")
class KakaoUser(
    id: String,
    nickname: String,
    email: String?,

    @Column(nullable = false)
    var connectedAt: String,

    @Column(nullable = true)
    var profileImageUrl: String?,

    @Column(nullable = true)
    var thumbnailImageUrl: String?,

    ) : User(
    id = id,
    displayName = nickname,
    userType = UserType.KAKAO,
    profileUrl = thumbnailImageUrl,
    email = email,
)