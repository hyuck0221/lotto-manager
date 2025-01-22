package com.hshim.lottomanager.api.account.admin

import com.hshim.lottomanager.model.account.user.UserResponse
import com.hshim.lottomanager.service.account.admin.AdminCommandService
import com.hshim.lottomanager.service.account.admin.AdminQueryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/account/admin")
class AdminController(
    private val adminQueryService: AdminQueryService,
    private val adminCommandService: AdminCommandService,
) {
    @GetMapping("/search")
    fun search(@RequestParam(required = false) name: String?): List<UserResponse> {
        return adminQueryService.search(name)
    }

    @PostMapping("/{id}")
    fun init(@PathVariable id: String) = adminCommandService.init(id)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String) = adminCommandService.delete(id)
}