package com.hshim.lottomanager.controller.page.simulation

import com.hshim.lottomanager.annotation.PublicEndpoint
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/page/simulation")
class SimulationPageController {
    @GetMapping("/main")
    @PublicEndpoint
    fun main() = "page/simulation/main"
}