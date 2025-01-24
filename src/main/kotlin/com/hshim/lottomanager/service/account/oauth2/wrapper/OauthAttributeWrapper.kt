package com.hshim.lottomanager.service.account.oauth2.wrapper

import com.hshim.lottomanager.enums.account.UserType
import com.hshim.lottomanager.model.account.oauth2.OauthAttribute

interface OauthAttributeWrapper {
    fun support(userType: UserType): Boolean
    fun getAttribute(attributeMap: Map<String, Any>): OauthAttribute
}