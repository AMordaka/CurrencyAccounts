package com.example.demo.controller

import com.example.demo.model.CurrencyCode
import com.example.demo.service.AccountService
import com.example.demo.service.BadRequestException
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.util.*

@RestController
@RequestMapping("/api/v1/accounts")
private class AccountController(private val accountService: AccountService) {

    @PostMapping
    @Operation(summary = "Register new account")
    fun registerAccount(@RequestBody newUser: NewAccountDto): AccountDto {
        return accountService.registerAccount(newUser)
    }

    @GetMapping(path = ["/{pesel}"])
    @Operation(summary = "Get account with given pese;")
    fun getAccount(@PathVariable("pesel") pesel: String): AccountDto {
        return accountService.findAccountByPesel(pesel)
    }

    @PostMapping(path = ["/{pesel}/exchange"])
    @Operation(summary = "Exchange value from sub account to another sub account")
    fun exchange(
        @PathVariable("pesel") pesel: String,
        @RequestParam value: BigDecimal,
        @RequestParam from: CurrencyCode,
        @RequestParam to: CurrencyCode,
    ): AccountDto {
        if (from != to) {
            return accountService.exchange(pesel, value, from, to)
        }
        throw BadRequestException("Currency codes are that same")

    }
}

data class NewAccountDto(
    val firstName: String,
    val lastName: String,
    val pesel: String,
    val balancePln: BigDecimal
)

data class AccountDto(
    val id: UUID?,
    val firstName: String,
    val lastName: String,
    val pesel: String,
    val accounts: List<SubAccountDto>
)

data class SubAccountDto(
    val id: UUID?,
    val currency: CurrencyCode,
    val balance: BigDecimal
)