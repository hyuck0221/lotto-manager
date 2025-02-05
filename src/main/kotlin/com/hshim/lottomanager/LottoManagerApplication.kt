package com.hshim.lottomanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.TimeZone

@SpringBootApplication
@EnableScheduling
@EnableAsync
class LottoManagerApplication

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    runApplication<LottoManagerApplication>(*args)
}
