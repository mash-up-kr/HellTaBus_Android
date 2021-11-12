/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.data.api

import com.mashup.healthyup.data.request.PostExerciseRequest
import com.mashup.healthyup.data.response.GetExerciseResponse
import com.mashup.healthyup.data.response.GetExerciseSuggestionResponse
import com.mashup.healthyup.data.response.PatchExerciseResponse
import com.mashup.healthyup.data.response.PostExerciseResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ExerciseApi {

    @POST("exercise")
    suspend fun postExercise(request: PostExerciseRequest): PostExerciseResponse

    @GET("exercise")
    suspend fun getExercise(
        @Query("partList") partList: String = "" // lower,back
    ): GetExerciseResponse

    @PATCH("exercise/{id}")
    suspend fun patchExercise(
        @Path("id") id: Long
    ): PatchExerciseResponse

    @DELETE("exercise/{id}")
    suspend fun deleteExercise(
        @Path("id") id: Long
    )

    @GET("exercise/suggestion")
    suspend fun getExerciseSuggestion(
        @Query("from") from: String = "",
        @Query("to") to: String = ""
    ): GetExerciseSuggestionResponse
}
