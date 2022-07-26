package com.example.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@EnableJpaRepositories(basePackages = ["com.example.demo.repository"])
@EnableTransactionManagement
internal class Config {

    @Bean
    fun webClient(): WebClient {
        return WebClient.builder()
            .baseUrl(NBP_URL)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()
    }

    private companion object {
        const val NBP_URL = "https://api.nbp.pl/api"
    }
}
