/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.data.api

import com.mashup.healthyup.data.request.IdToken
import com.mashup.healthyup.data.request.UserRequest
import com.mashup.healthyup.data.response.TokenResponse
import com.mashup.healthyup.data.response.user.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface UserApi {

    @POST("user")
    suspend fun register(@Body token: IdToken): TokenResponse

    @POST("user/login")
    suspend fun signIn(@Body token: IdToken): TokenResponse

    @GET("user/login-info")
    suspend fun getCurrentUser(): UserResponse

    @PATCH("user/my/base-information")
    suspend fun patchBaseInformation(request: UserRequest): UserResponse

    @PATCH("user/my")
    suspend fun update(request: UserRequest): UserResponse
}
