package com.hshim.lottomanager.service.account.oauth2

import com.hshim.lottomanager.database.account.email.EmailVerify
import com.hshim.lottomanager.database.account.email.repository.EmailVerifyRepository
import com.hshim.lottomanager.database.account.repository.UserRepository
import com.hshim.lottomanager.enums.account.UserType
import com.hshim.lottomanager.exception.GlobalException
import com.hshim.lottomanager.service.account.oauth2.wrapper.OauthAttributeWrapper
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository,
    private val emailVerifyRepository: EmailVerifyRepository,
    private val oauthAttributeWrappers: List<OauthAttributeWrapper>,
) : DefaultOAuth2UserService() {
    @Transactional
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        if (userRepository.existsById(oAuth2User.name)) return oAuth2User

        val wrapper = UserType.findByRegistrationId(userRequest.clientRegistration.registrationId)
            ?.let { userType -> oauthAttributeWrappers.find { it.support(userType) } }
            ?: throw GlobalException.NOT_SUPPORT_OAUTH.exception

        val user = userRepository.save(wrapper.getAttribute(oAuth2User.attributes).toEntity())
        if (user.email != null) emailVerifyRepository.save(EmailVerify(user))
        return oAuth2User
    }
}