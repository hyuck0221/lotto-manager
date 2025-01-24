package com.hshim.lottomanager.service.account.oauth2.wrapper

import com.hshim.lottomanager.enums.account.UserType
import com.hshim.lottomanager.model.account.oauth2.KakaoOauthAttribute
import com.hshim.lottomanager.model.account.oauth2.OauthAttribute
import org.springframework.stereotype.Component

@Component
class KakaoOauthAttributeWrapper : OauthAttributeWrapper {
    override fun support(userType: UserType): Boolean = userType == UserType.KAKAO
    override fun getAttribute(attributeMap: Map<String, Any>): OauthAttribute = KakaoOauthAttribute(attributeMap)
}