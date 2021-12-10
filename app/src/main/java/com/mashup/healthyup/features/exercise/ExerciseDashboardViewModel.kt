/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.features.exercise

import com.mashup.healthyup.base.BaseViewModel
import com.mashup.healthyup.domain.entity.Exercise
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExerciseDashboardViewModel @Inject constructor(
    private val params: Params
) : BaseViewModel() {

    data class Params(val exerciseList: List<Exercise>)
}
