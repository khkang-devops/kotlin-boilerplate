package com.test.kotlinboilerplate.common.exeption

import jakarta.validation.UnexpectedTypeException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(
        MethodArgumentNotValidException::class,
        UnexpectedTypeException::class,
        HttpMessageNotReadableException::class,
        ServletRequestBindingException::class
    )
    fun handleBadRequestException(ex: Exception): ResponseEntity<ErrorResponse> {
        return getResponseEntity(HttpStatus.BAD_REQUEST, ex)
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNotFoundException(ex: Exception): ResponseEntity<ErrorResponse> {
        return getResponseEntity(HttpStatus.NOT_FOUND, ex)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleMethodNotAllowedException(ex: Exception): ResponseEntity<ErrorResponse> {
        return getResponseEntity(HttpStatus.METHOD_NOT_ALLOWED, ex)
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun handleUnsupportedMediaTypeException(ex: Exception): ResponseEntity<ErrorResponse> {
        return getResponseEntity(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex)
    }

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(ex: Exception): ResponseEntity<ErrorResponse> {
        return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex)
    }

    private fun getResponseEntity(
        status: HttpStatus,
        ex: Exception
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = status.value(),
            message = ex.message ?: "Internal Server Error"
        )

        return ResponseEntity
            .status(status)
            .body(errorResponse)
    }
}

data class ErrorResponse(
    val status: Int = HttpStatus.INTERNAL_SERVER_ERROR.value(),
    val message: String
)