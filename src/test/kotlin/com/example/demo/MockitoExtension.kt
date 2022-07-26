package com.example.demo

import org.mockito.Mockito

fun <T> anyObject(): T {
    Mockito.any<T>()
    return uninitialized()
}

fun <T> any(type: Class<T>): T = Mockito.any(type)

@Suppress("UNCHECKED_CAST")
private fun <T> uninitialized(): T = null as T

fun <T> eq(obj: T): T = Mockito.eq(obj)