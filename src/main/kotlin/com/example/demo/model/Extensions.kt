package com.example.demo.model

import com.example.demo.model.db.Account
import com.example.demo.model.db.CurrencyCode
import com.example.demo.model.db.SubAccount
import com.example.demo.model.dto.AccountDto
import com.example.demo.model.dto.NewAccountDto
import com.example.demo.model.dto.SubAccountDto
import java.math.BigDecimal
import java.math.RoundingMode

internal fun Account.toAccountDto() =
    AccountDto(id, firstName, lastName, pesel, subAccounts.map { SubAccountDto(it.id, it.currencyCode, it.balance) })

internal fun NewAccountDto.toAccount(): Account {
    val account = Account(pesel = pesel, firstName = firstName, lastName = lastName)
    return account.copy(
        subAccounts = mutableListOf(
            SubAccount(
                currencyCode = CurrencyCode.PLN,
                balance = balancePln,
                account = account
            ),
            SubAccount(
                currencyCode = CurrencyCode.USD,
                balance = BigDecimal.ZERO,
                account = account
            )
        )
    )
}

internal fun BigDecimal.customScale(newScale: Int = 4): BigDecimal = this.setScale(newScale, RoundingMode.HALF_EVEN)