package com.hshim.lottomanager.model.account.oauth2

import com.hshim.lottomanager.enums.account.UserType

class KakaoOauthAttribute(
    id: Long,
    val connectedAt: String,
    val kakaoAccount: KakaoAccount,
) : OauthAttribute(
    userType = UserType.KAKAO,
    id = id.toString(),
    displayName = kakaoAccount.profile.nickname,
    profileUrl = kakaoAccount.profile.thumbnailImageUrl,
    email = kakaoAccount.email,
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
}