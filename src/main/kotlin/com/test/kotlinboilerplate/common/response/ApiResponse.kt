package com.test.kotlinboilerplate.common.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.test.kotlinboilerplate.common.code.ResultCode
import io.swagger.v3.oas.annotations.media.Schema

@JsonInclude(JsonInclude.Include.NON_NULL)
class ApiResponse<T>(
    @Schema(description = "응답 코드", example = "0000")
    val code: String = ResultCode.SUCCESS.code,

    @Schema(description = "응답 메시지", example = "성공")
    val message: String? = null,

    @Schema(description = "응답 DTO")
    val data: T? = null,
) {
    constructor(resultCode: ResultCode): this(
        code = resultCode.code,
        message = resultCode.message,
    )
}