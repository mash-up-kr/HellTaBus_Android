package com.mashup.healthyup.data.exceptions

sealed class ApiException : Exception() {
    abstract val errorMessage: String

    data class BadRequest(override val errorMessage: String) : ApiException()
    data class InternalServerError(override val errorMessage: String) : ApiException()
    data class Unknown(override val errorMessage: String) : ApiException()
}
