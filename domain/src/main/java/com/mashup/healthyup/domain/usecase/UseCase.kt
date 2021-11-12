/*
 * Created by Leo on 2021. 09. 26 ..
 */
package com.mashup.healthyup.domain.usecase

import com.mashup.healthyup.domain.NoParamsException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface UseCase

abstract class ParameterizedUseCase<in Params, R>(
    private val dispatcher: CoroutineDispatcher
) : UseCase {

    protected abstract suspend fun execute(params: Params): R
    
    suspend operator fun invoke(params: Params) : Result<R> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                execute(params)
            }
        }
    }
}

abstract class NoParamsUseCase<R>(private val dispatcher: CoroutineDispatcher) : UseCase {

    protected abstract suspend fun execute(): R
    
    suspend operator fun invoke() : Result<R> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                execute()
            }
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
