package com.hshim.lottomanager.service.lotto.template

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hshim.lottomanager.model.lotto.LottoTemplateResponse
import org.springframework.web.client.RestTemplate

class LottoTemplate(
    private val times: Int,
    private val url: String,
) : RestTemplate() {
    fun getInfo(): LottoTemplateResponse? {
        return try {
            val response = super.getForEntity(
                "$url/common.do?method=getLottoNumber&drwNo=$times",
                String::class.java,
            ).body ?: return null
            jacksonObjectMapper().readValue(
                response,
                LottoTemplateResponse::class.java
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}