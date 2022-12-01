package com.example.demo.controller

import com.example.demo.model.db.CurrencyCode
import com.example.demo.model.dto.AccountDto
import com.example.demo.model.dto.NewAccountDto
import com.example.demo.model.exception.InternalException
import com.example.demo.service.AccountService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/v1/accounts")
private class AccountController(private val accountService: AccountService) {

    @PostMapping
    @Operation(summary = "Register new account")
    fun registerAccount(@RequestBody newUser: NewAccountDto): AccountDto {
        return accountService.registerAccount(newUser)
    }

    @GetMapping(path = ["/{pesel}"])
    @Operation(summary = "Get account with given pesel")
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
        throw InternalException("Currency codes are that same")

    }

}

