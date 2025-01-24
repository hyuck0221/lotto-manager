package com.hshim.lottomanager.service.account.oauth2.wrapper

import com.hshim.lottomanager.enums.account.UserType
import com.hshim.lottomanager.model.account.oauth2.GoogleOauthAttribute
import com.hshim.lottomanager.model.account.oauth2.OauthAttribute
import org.springframework.stereotype.Component

@Component
class GoogleOauthAttributeWrapper : OauthAttributeWrapper {
    override fun support(userType: UserType): Boolean = userType == UserType.GOOGLE
    override fun getAttribute(attributeMap: Map<String, Any>): OauthAttribute = GoogleOauthAttribute(attributeMap)
}