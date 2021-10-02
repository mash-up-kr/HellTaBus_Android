package com.mashup.healthyup.data.response

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class WrapperResponseConvertFactory(
    private val gsonConverterFactory: GsonConverterFactory
) : Converter.Factory() {

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return gsonConverterFactory.requestBodyConverter(
            type,
            parameterAnnotations,
            methodAnnotations,
            retrofit
        )
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val converter: Converter<ResponseBody, *>? =
            gsonConverterFactory.responseBodyConverter(type, annotations, retrofit)
        return WrapperResponseConverter(converter as Converter<ResponseBody, Any>)
    }
}
