package com.test.kotlinboilerplate.api.sample

import com.test.kotlinboilerplate.common.code.ResultCode
import com.test.kotlinboilerplate.common.response.ApiResponse
import org.springframework.web.bind.annotation.*

@RestController
class SampleController(
    private val sampleService: SampleService
) {
    @PostMapping("/api/samples/list")
    fun getSampleList(@RequestBody request: SampleRequestDto): ApiResponse<List<SampleResponseDto>> {
        return ApiResponse(data=sampleService.getSampleList(request))
    }

    @PostMapping("/api/samples/list/count")
    fun getSampleListCount(@RequestBody request: SampleRequestDto): ApiResponse<Long> {
        return ApiResponse(data=sampleService.getSampleListCount(request))
    }

    @GetMapping("/api/samples/{id}")
    fun getSample(@PathVariable id: String): ApiResponse<SampleResponseDto> {
        return ApiResponse(data=sampleService.getSample(id))
    }

    @PostMapping("/api/samples/insert")
    fun insertSample(@RequestBody request: SampleRequestDto): ApiResponse<Void> {
        sampleService.insertSample(request)
        return ApiResponse(ResultCode.SUCCESS)
    }

    @PostMapping("/api/samples/update")
    fun updateSample(@RequestBody request: SampleRequestDto): ApiResponse<Void> {
        sampleService.updateSample(request)
        return ApiResponse(ResultCode.SUCCESS)
    }

    @PostMapping("/api/samples/delete/{id}")
    fun deleteSample(@PathVariable id: String): ApiResponse<Void> {
        sampleService.deleteSample(id)
        return ApiResponse(ResultCode.SUCCESS)
    }

    @PostMapping("/api/samples/list/native")
    fun getSampleListNative(@RequestBody request: SampleRequestDto): ApiResponse<List<SampleResponseDto>> {
        return ApiResponse(data=sampleService.getSampleListNative(request))
    }
}