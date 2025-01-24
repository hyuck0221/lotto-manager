package com.hshim.lottomanager.service.account.oauth2.wrapper

import com.hshim.lottomanager.enums.account.UserType
import com.hshim.lottomanager.model.account.oauth2.GithubOauthAttribute
import com.hshim.lottomanager.model.account.oauth2.OauthAttribute
import org.springframework.stereotype.Component

@Component
class GithubOauthAttributeWrapper : OauthAttributeWrapper {
    override fun support(userType: UserType): Boolean = userType == UserType.GITHUB
    override fun getAttribute(attributeMap: Map<String, Any>): OauthAttribute = GithubOauthAttribute(attributeMap)
}