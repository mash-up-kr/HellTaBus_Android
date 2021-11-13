package com.mashup.healthyup.data.response.history

import com.mashup.healthyup.data.response.BaseResponse
import com.mashup.healthyup.domain.entity.ExerciseHistory

data class GetExerciseHistoryResponse(
    override val data: List<ExerciseHistory?>
) : BaseResponse<List<ExerciseHistory?>>()
