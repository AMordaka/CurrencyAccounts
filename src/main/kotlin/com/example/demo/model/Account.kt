package com.example.demo.model

import org.hibernate.annotations.NotFound
import org.hibernate.annotations.NotFoundAction
import org.hibernate.annotations.Type
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
internal data class SubAccount(
    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),
    val currencyCode: CurrencyCode = CurrencyCode.PLN,
    val balance: BigDecimal = BigDecimal.ZERO,
    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    val account: Account? = null
)

@Entity
internal data class Account(
    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),
    @Column(unique = true)
    val pesel: String = "",
    val firstName: String = "",
    val lastName: String = "",

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val subAccounts: MutableList<SubAccount> = mutableListOf()
)


enum class CurrencyCode {
    USD,
    PLN
}