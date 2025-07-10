package com.test.kotlinboilerplate.common.code

enum class ResultCode(val code: String, val message: String) {
    SUCCESS("0000", "성공"),
    FAIL("9999", "오류"),
}