package com.example.demo.controller

import com.example.demo.model.exception.InternalException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler(InternalException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInternalException(exception: InternalException): ResponseEntity<Map<String, String>> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(mapOf("message" to exception.localizedMessage))
    }

}
