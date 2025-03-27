package com.hshim.lottomanager.controller.page.simulation

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/page/simulation")
class SimulationPageController {
    @GetMapping("/main")
    fun main() = "page/simulation/main"
}