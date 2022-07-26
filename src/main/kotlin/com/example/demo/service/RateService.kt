package com.example.demo.service

import com.example.demo.model.CurrencyCode
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import java.math.BigDecimal

internal interface RateService {
    fun getRate(currency: CurrencyCode): ExchangeRatesSeries

}

@Service
internal class NBPRateService(private val webClient: WebClient) : RateService {
    override fun getRate(currency: CurrencyCode): ExchangeRatesSeries {
        webClient.get().uri("/exchangerates/rates/a/${currency}?format=json")
            .retrieve()
            .bodyToMono<ExchangeRatesSeries>()
            .block()?.let { return it }
        throw BadRequestException("Unable fetch exchange rates")
    }
}

internal data class ExchangeRatesSeries(
    val table: String,
    val currency: String,
    val code: String,
    val rates: List<Rate>
)

internal data class Rate(
    val no: String,
    val effectiveDate: String,
    val mid: BigDecimal,
)