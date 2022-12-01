package com.example.demo.service

import com.example.demo.model.customScale
import com.example.demo.model.db.CurrencyCode
import com.example.demo.model.db.SubAccount
import com.example.demo.model.dto.AccountDto
import com.example.demo.model.dto.NewAccountDto
import com.example.demo.model.exception.InternalException
import com.example.demo.model.toAccount
import com.example.demo.model.toAccountDto
import com.example.demo.repository.AccountRepository
import com.example.demo.repository.SubAccountRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Service
internal class AccountService(
    private val accountRepository: AccountRepository,
    private val subAccountRepository: SubAccountRepository,
    private val rateService: RateService
) {

    fun registerAccount(accountDto: NewAccountDto): AccountDto {
        verifyPesel(accountDto.pesel)
        if (accountRepository.existsByPesel(accountDto.pesel)) {
            throw InternalException("Pesel exists in system")
        }
        return accountRepository.save(accountDto.toAccount()).toAccountDto()
    }

    fun findAccountByPesel(pesel: String): AccountDto {
        accountRepository.findByPesel(pesel)?.let {
            return it.toAccountDto()
        }
        throw InternalException("Account with given pesel not found")
    }

    fun exchange(pesel: String, value: BigDecimal, from: CurrencyCode, to: CurrencyCode): AccountDto {
        accountRepository.findByPesel(pesel)?.let {
            val fromAccount = it.subAccounts.first { account -> account.currencyCode == from }
            val toAccount = it.subAccounts.first { account -> account.currencyCode == to }
            exchangeMoney(fromAccount, toAccount, value)
            return it.toAccountDto()
        }
        throw InternalException("Account with given pesel not found")
    }

    fun exchangeMoney(fromAccount: SubAccount, toAccount: SubAccount, value: BigDecimal) {
        if (fromAccount.balance < value) {
            throw InternalException("There are not enough funds in the account")
        }
        val rate = rateService.getRate(CurrencyCode.USD).rates.first().mid
        when (fromAccount.currencyCode) {
            CurrencyCode.PLN -> {
                val updatedFromAccount = fromAccount.copy(balance = fromAccount.balance.minus(value).customScale(2))
                val updatedToAccount =
                    toAccount.copy(
                        balance = toAccount.balance.plus(value.customScale().divide(rate, RoundingMode.HALF_EVEN))
                            .customScale(2)
                    )

                subAccountRepository.save(updatedFromAccount)
                subAccountRepository.save(updatedToAccount)
            }

            CurrencyCode.USD -> {
                val updatedFromAccount = fromAccount.copy(balance = fromAccount.balance.minus(value).customScale(2))
                val updatedToAccount =
                    toAccount.copy(
                        balance = toAccount.balance.plus(value.customScale().multiply(rate)).customScale(2)
                    )
                subAccountRepository.save(updatedFromAccount)
                subAccountRepository.save(updatedToAccount)
            }
        }

    }

    private fun verifyPesel(pesel: String) {
        if (pesel.length != 11) throw InternalException("Incorrect pesel length")
        val year = pesel.take(2).toInt()
        val month = pesel.substring(2, 4).toInt()
        val day = pesel.substring(4, 6).toInt()
        val date = when (month) {
            in 1..12 -> LocalDate.of(1900 + year, month, day)
            in 21..32 -> LocalDate.of(2000 + year, month - 20, day)
            in 40..52 -> LocalDate.of(2100 + year, month - 40, day)
            else -> throw InternalException("Invalid pesel")
        }
        if (ChronoUnit.YEARS.between(date, LocalDate.now()) < 18) {
            throw InternalException("Age must be equal or over 18")
        }
    }

}
