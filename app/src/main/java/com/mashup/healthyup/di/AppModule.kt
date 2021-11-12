package com.mashup.healthyup.di

import com.mashup.healthyup.ExecutorProvider
import com.mashup.healthyup.domain.repository.UserRepository
import com.mashup.healthyup.domain.usecase.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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

    // Logger 주입

    // 전역 Config
}
