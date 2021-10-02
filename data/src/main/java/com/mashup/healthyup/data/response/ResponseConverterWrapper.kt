package com.mashup.healthyup.data.response

import com.mashup.healthyup.data.exceptions.ApiException
import okhttp3.ResponseBody
import retrofit2.Converter

class ResponseConverterWrapper<T>(
    private val converter: Converter<ResponseBody, T>
) : Converter<ResponseBody, T> {

    override fun convert(value: ResponseBody): T? {
        val response = converter.convert(value)
        if (response is BaseResponse<*>) {
            when (response.status) {
                in 200..299 -> {
                    return response
                }
                in 400..499 -> {
                    throw ApiException.BadRequest(response.message.orEmpty())
                }
                in 500..599 -> {
                    throw ApiException.InternalServerError(response.message.orEmpty())
                }
                else -> {
                    throw ApiException.Unknown(response.message.orEmpty())
                }
            }
        }
        return response
    }
}
