package com.mashup.healthyup.data.response

abstract class BaseResponse<D> {
    val status: Int? = null
    val message: String? = null
    abstract val data: D?
}
