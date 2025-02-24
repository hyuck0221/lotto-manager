package com.hshim.lottomanager.api.account.email

import com.hshim.lottomanager.annotation.PublicEndpoint
import com.hshim.lottomanager.service.account.email.EmailVerifyCommandService
import com.hshim.lottomanager.service.account.user.UserQueryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/account/email")
class EmailController(
    private val emailVerifyCommandService: EmailVerifyCommandService,
    private val userQueryService: UserQueryService,
) {

    @PostMapping("/verify")
    fun verify(
        @RequestParam(required = true) email: String,
        @RequestParam(required = true) code: String,
    ) = emailVerifyCommandService.verify(email, code)

    @PostMapping("/verify/build-code")
    fun verifyCode(@RequestParam(required = true) email: String) {
        val user = userQueryService.getUser()
        emailVerifyCommandService.buildCode(user, email)
    }

    @DeleteMapping("/verify/{id}")
    @PublicEndpoint
    fun deleteVerify(@PathVariable id: String) {
        emailVerifyCommandService.deleteVerify(id)
    }
}