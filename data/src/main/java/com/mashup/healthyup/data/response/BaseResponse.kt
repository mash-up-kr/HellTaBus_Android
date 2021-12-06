package com.mashup.healthyup.data.response

abstract class BaseResponse<D> {
    open val code: Int? = null
    val message: String? = null
    abstract val data: D?
}
