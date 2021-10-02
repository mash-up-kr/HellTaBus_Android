/*
 * Created by Leo on 2021. 09. 26 ..
 */
package com.mashup.healthyup.domain.usecase

import com.mashup.healthyup.domain.Result
import com.mashup.healthyup.domain.entity.User
import com.mashup.healthyup.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetUserUseCase(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher
) : ParameterizedUseCase<Long, User>(dispatcher) {
    
    override suspend fun execute(userId: Long): Result<User> {
        return userRepository.getUser(userId)
    }
}
