package com.mashup.healthyup.di

import com.mashup.healthyup.ExecutorProvider
import com.mashup.healthyup.data.repository.ExerciseHistoryRepositoryImpl
import com.mashup.healthyup.data.repository.ExerciseRepositoryImpl
import com.mashup.healthyup.domain.repository.ExerciseHistoryRepository
import com.mashup.healthyup.domain.repository.ExerciseRepository
import com.mashup.healthyup.domain.repository.UserRepository
import com.mashup.healthyup.domain.usecase.GetUserUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindExerciseRepository(repository: ExerciseRepositoryImpl): ExerciseRepository

    @Binds
    abstract fun bindExerciseHistoryRepository(
        repository: ExerciseHistoryRepositoryImpl
    ): ExerciseHistoryRepository

    companion object {

        @Singleton
        @Provides
        fun provideExecutorProvider(): ExecutorProvider {
            return object : ExecutorProvider {
                override fun main(): CoroutineDispatcher {
                    return Dispatchers.Main
                }

                override fun io(): CoroutineDispatcher {
                    return Dispatchers.IO
                }

                override fun default(): CoroutineDispatcher {
                    return Dispatchers.Default
                }

                override fun unconfined(): CoroutineDispatcher {
                    return Dispatchers.Unconfined
                }
            }
        }

        @Provides
        fun provideGetUserUseCase(
            userRepository: UserRepository,
            executorProvider: ExecutorProvider
        ): GetUserUseCase {
            return GetUserUseCase(userRepository, executorProvider.io())
        }
    }
}
