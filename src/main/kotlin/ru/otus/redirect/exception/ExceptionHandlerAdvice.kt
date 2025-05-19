package ru.otus.redirect.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler(RulePackAlreadyExistsException::class)
    fun handle(exc: RulePackAlreadyExistsException) =
        ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(exc.message)

    @ExceptionHandler(RulePackNotExistsException::class, ConditionNotFoundException::class)
    fun handle(exc: Exception) =
        ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(exc.message)
}
