/*
 * Created by Leo on 2021. 09. 26 ..
 */
package com.mashup.healthyup.domain.usecase

import com.mashup.healthyup.domain.NoParamsException
import com.mashup.healthyup.domain.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface UseCase

abstract class ParameterizedUseCase<in Params, R>(
    private val dispatcher: CoroutineDispatcher
) : UseCase {
    protected abstract suspend fun execute(params: Params): Result<R>
    
    suspend operator fun invoke(params: Params) : Result<R> {
        return try {
            withContext(dispatcher) {
                execute(params)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}

abstract class NoParamsUseCase<R>(private val dispatcher: CoroutineDispatcher) : UseCase {
    protected  abstract suspend fun execute(): Result<R>
    
    suspend operator fun invoke() : Result<R> {
        return try {
            withContext(dispatcher) {
                execute()
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}

// ParameterizedUseCase 의 Params 로 전달된 인자가 null 이라면, throw exception.
fun <T: Any> ParameterizedUseCase<*, *>.requireParamsNonNull(value: T?): T {
    if(value == null) {
        throw NoParamsException()
    } else {
        return value
    }
}
