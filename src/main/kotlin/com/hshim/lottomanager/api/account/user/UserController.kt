package com.hshim.lottomanager.api.account.user

import com.hshim.lottomanager.model.account.user.UserRequest
import com.hshim.lottomanager.model.account.user.UserResponse
import com.hshim.lottomanager.service.account.user.UserCommandService
import com.hshim.lottomanager.service.account.user.UserQueryService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/account/user")
class UserController(
    private val userQueryService: UserQueryService,
    private val userCommandService: UserCommandService,
) {
    @GetMapping("/my-info")
    fun myInfo(): UserResponse {
        return UserResponse(userQueryService.getUser())
    }

    @GetMapping("/{id}")
    fun info(@PathVariable id: String): UserResponse {
        return userQueryService.getInfo(id)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody req: UserRequest,
    ): UserResponse {
        return userCommandService.update(id, req)
    }

    @PutMapping("/{id}/synchronization")
    fun synchronization(
        @PathVariable id: String,
        authentication: OAuth2AuthenticationToken,
    ): UserResponse {
        return userCommandService.synchronization(id, authentication)
    }

    @GetMapping("/search")
    fun search(@RequestParam name: String): List<UserResponse> {
        return userQueryService.search(name).map { UserResponse(it) }
    }
}