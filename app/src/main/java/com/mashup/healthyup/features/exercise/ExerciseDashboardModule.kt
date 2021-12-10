/*
 * Created by Leo on 2021. 12. 11 ..
 */
package com.mashup.healthyup.features.exercise

import android.annotation.SuppressLint
import androidx.lifecycle.SavedStateHandle
import com.mashup.healthyup.Key
import com.mashup.healthyup.domain.entity.Exercise
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import java.text.SimpleDateFormat
import java.util.Date

@Module
@InstallIn(ViewModelComponent::class)
object ExerciseDashboardModule {

    @SuppressLint("SimpleDateFormat")
    @Provides
    @ViewModelScoped
    fun provideParams(handle: SavedStateHandle): ExerciseDashboardViewModel.Params {
        val exerciseList = handle.get<List<Exercise>>(Key.EXERCISE_LIST) ?: emptyList()
        val todayDate = SimpleDateFormat("yyyy-MM-dd").format(Date()).toString()

        return ExerciseDashboardViewModel.Params(exerciseList, todayDate)
    }
}
