/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.data.api

import com.mashup.healthyup.data.request.PostExerciseHistoryRequest
import com.mashup.healthyup.data.response.history.GetExerciseHistoryResponse
import com.mashup.healthyup.data.response.history.PostExerciseHistoryResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ExerciseHistoryApi {

    @POST("exercise-history")
    suspend fun postExerciseHistory(
        request: PostExerciseHistoryRequest
    ): PostExerciseHistoryResponse

    @GET("exercise-history")
    suspend fun getExerciseHistory(
        @Query("exerciseIdList") exerciseIdList: String, // 1,2,3... required
        @Query("duration") duration: String?, // recent
        @Query("from") from: String?, // 2021-10-22 13:32
        @Query("to") to: String? // 2021-10-24 13:32
    ): GetExerciseHistoryResponse
}
