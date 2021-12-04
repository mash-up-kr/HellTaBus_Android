/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.data.repository

import com.mashup.healthyup.data.api.UserApi
import com.mashup.healthyup.data.request.toRequest
import com.mashup.healthyup.domain.entity.User
import com.mashup.healthyup.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserRepository {

    override suspend fun register() {
        // TODO: local 에 저장되어 있던 token (?) 을 가지고 와서 idToken 으로 만든후에 api 호출
        // TODO: local 에 토큰을 저장해야 한다면, preference 를 사용할 수 있지 않을까?
    }

    override suspend fun signIn() {
        // TODO: local 에 저장되어 있던 token (?) 을 가지고 와서 idToken 으로 만든후에 api 호출
        // TODO: local 에 토큰을 저장해야 한다면, preference 를 사용할 수 있지 않을까?
    }

    override suspend fun getCurrentUser(): User {
        return userApi.getCurrentUser().data ?: User.EMPTY
    }

    override suspend fun patchBaseInformation(user: User): User {
        val request = user.toRequest()
        val response = userApi.patchBaseInformation(request)
        return response.data ?: User.EMPTY
    }

    override suspend fun update(user: User): User {
        val request = user.toRequest()
        val response = userApi.update(request)
        return response.data ?: User.EMPTY
    }
}
