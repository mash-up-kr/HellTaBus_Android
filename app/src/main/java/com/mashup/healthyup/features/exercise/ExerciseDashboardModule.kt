/*
 * Created by Leo on 2021. 12. 11 ..
 */
package com.mashup.healthyup.features.exercise

import androidx.lifecycle.SavedStateHandle
import com.mashup.healthyup.Key
import com.mashup.healthyup.domain.entity.Exercise
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ExerciseDashboardModule {

    @Provides
    @ViewModelScoped
    fun provideParams(handle: SavedStateHandle): ExerciseDashboardViewModel.Params {
        val exerciseList = handle.get<List<Exercise>>(Key.EXERCISE_LIST) ?: emptyList()

        return ExerciseDashboardViewModel.Params(exerciseList)
    }
}
