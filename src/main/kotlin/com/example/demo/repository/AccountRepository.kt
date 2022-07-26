package com.example.demo.repository

import com.example.demo.model.Account
import com.example.demo.model.SubAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
internal interface AccountRepository : JpaRepository<Account, UUID> {
    fun findByPesel(pesel: String): Account?
    fun existsByPesel(pesel: String): Boolean
}

@Repository
internal interface SubAccountRepository : JpaRepository<SubAccount, UUID>