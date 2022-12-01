package com.example.demo.model.dto

import com.example.demo.model.db.CurrencyCode
import java.math.BigDecimal
import java.util.*

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