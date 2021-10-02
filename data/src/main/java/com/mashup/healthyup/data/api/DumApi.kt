package com.mashup.healthyup.data.api

import com.mashup.healthyup.data.request.DaangnDto
import com.mashup.healthyup.data.response.Dum
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DumApi {
    @GET("/{path}")
    fun getDumUser(
        @Path("path") id: String
    ): Dum

    @POST("/")
    fun postDumUser(
        @Body daangn: DaangnDto
    ): Dum
}
