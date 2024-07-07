package com.ing.assigment.errorhandling

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String?>> {
        val fieldErrors = ex.bindingResult.fieldErrors.associate { it.field to it.defaultMessage }
        val globalErrors = ex.bindingResult.globalErrors.associate { it.objectName to it.defaultMessage }
        return ResponseEntity.status(BAD_REQUEST).body(fieldErrors + globalErrors)
    }

    @ExceptionHandler(IllegalArgumentException::class, NotFoundException::class, HttpMessageNotReadableException::class)
    fun handleIllegalArgumentExceptions(ex: Throwable): ResponseEntity<Map<String, String?>> {
        return ResponseEntity.status(BAD_REQUEST).body(mapOf("error" to ex.message))
    }

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(ex: Exception, request: WebRequest): ResponseEntity<Map<String, String?>> {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(mapOf("error" to ex.message))
    }
}
