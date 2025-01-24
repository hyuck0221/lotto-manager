package com.hshim.lottomanager.model.account.oauth2

import com.hshim.lottomanager.database.account.KakaoUser

class KakaoOauthAttribute(
    val id: Long,
    val connectedAt: String,
    val kakaoAccount: KakaoAccount,
) {
    class KakaoAccount(
        val profile: Profile,
        val email: String?,
    ) {
        class Profile(
            val nickname: String,
            val profileImageUrl: String?,
            val thumbnailImageUrl: String?,
        )
    }
    constructor(attributeMap: Map<String, Any>) : this(
        id = attributeMap["id"] as Long,
        connectedAt = attributeMap["connected_at"] as String,
        kakaoAccount = KakaoAccount(
            profile = with(((attributeMap["kakao_account"] as Map<*, *>)["profile"] as Map<*, *>)) {
                KakaoAccount.Profile(
                    nickname = this["nickname"] as String,
                    profileImageUrl = this["profile_image_url"] as String?,
                    thumbnailImageUrl = this["thumbnail_image_url"] as String?,
                )
            },
            email = (attributeMap["kakao_account"] as Map<*, *>)["email"] as String?,
        )
    )

    fun toEntity() = KakaoUser(
        id = id.toString(),
        nickname = kakaoAccount.profile.nickname,
        email = kakaoAccount.email,
        connectedAt = connectedAt,
        profileImageUrl = kakaoAccount.profile.profileImageUrl,
        thumbnailImageUrl = kakaoAccount.profile.thumbnailImageUrl,
    )

    fun updateTo(kakaoUser: KakaoUser) {
        kakaoUser.displayName = kakaoAccount.profile.nickname
        kakaoUser.email = kakaoAccount.email
        kakaoUser.connectedAt = connectedAt
        kakaoUser.profileImageUrl = kakaoAccount.profile.profileImageUrl
        kakaoUser.thumbnailImageUrl = kakaoAccount.profile.thumbnailImageUrl
    }
}