/*
 * Created by Leo on 2021. 09. 26 ..
 */
package com.mashup.healthyup.domain.usecase

import com.mashup.healthyup.domain.entity.AccessToken
import com.mashup.healthyup.domain.entity.User
import com.mashup.healthyup.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetUserUseCase(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher
) : NoParamsUseCase<User>(dispatcher) {

    override suspend fun execute(): User {
        return userRepository.getCurrentUser()
    }

    suspend fun signIn(idToken: String): AccessToken? {
        return userRepository.signIn(idToken)
    }
}
