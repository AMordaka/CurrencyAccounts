package com.example.demo

import com.example.demo.model.Account
import com.example.demo.model.CurrencyCode
import com.example.demo.model.SubAccount
import com.example.demo.model.customScale
import com.example.demo.repository.AccountRepository
import com.example.demo.repository.SubAccountRepository
import com.example.demo.service.AccountService
import com.example.demo.service.ExchangeRatesSeries
import com.example.demo.service.Rate
import com.example.demo.service.RateService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.math.BigDecimal
import java.math.RoundingMode

class AccountServiceTest {

    private val accountRepository = Mockito.mock(AccountRepository::class.java)
    private val subAccountRepository = Mockito.mock(SubAccountRepository::class.java)
    private val rateService = Mockito.mock(RateService::class.java)
    private val service = AccountService(accountRepository, subAccountRepository, rateService)

    @Test
    fun `transfer money test`() {
        val value = BigDecimal.valueOf(100)
        val rate = BigDecimal.valueOf(4.34)
        //100/4.34
        val result = value.customScale().divide(rate, RoundingMode.HALF_EVEN).customScale()
        //23.04+
        val newResult = result.multiply(rate).customScale(2)
        println(result)
        println(newResult)
    }

}
