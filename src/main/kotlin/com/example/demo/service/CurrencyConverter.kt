package com.example.demo.service

import com.example.demo.model.db.CurrencyCode
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Service

@Service
internal class CurrencyConverter : Converter<String, CurrencyCode> {
    override fun convert(value: String): CurrencyCode {
        return CurrencyCode.valueOf(value)
    }
}

