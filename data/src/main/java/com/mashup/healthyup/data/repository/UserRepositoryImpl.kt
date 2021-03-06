/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.data.repository

import android.util.Log
import com.mashup.healthyup.data.api.UserApi
import com.mashup.healthyup.data.request.IdToken
import com.mashup.healthyup.data.request.toRequest
import com.mashup.healthyup.domain.entity.AccessToken
import com.mashup.healthyup.domain.entity.User
import com.mashup.healthyup.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserRepository {

    override suspend fun register(idToken: String): AccessToken? {
        return try {
            val response = userApi.register(IdToken(idToken))
            response.data?.let { AccessToken(it.accessToken, 0) }
        } catch (e: Exception) {
            Log.e("Exception: ", e.toString())
            AccessToken(e.toString(), 0)
        }
        // TODO: local 에 저장되어 있던 token (?) 을 가지고 와서 idToken 으로 만든후에 api 호출
        // TODO: local 에 토큰을 저장해야 한다면, preference 를 사용할 수 있지 않을까?
    }

    override suspend fun signIn(idToken: String): AccessToken? {
        return try {
            val response = userApi.signIn(IdToken(idToken))
            if (response.code in 200..201) {
                response.data
            } else register(idToken)
        } catch (e: Exception) {
            Log.e("Exception: ", e.toString())
            register(idToken)
        }
        // TODO: local 에 저장되어 있던 token (?) 을 가지고 와서 idToken 으로 만든후에 api 호출
        // TODO: local 에 토큰을 저장해야 한다면, preference 를 사용할 수 있지 않을까?
    }

    override suspend fun getCurrentUser(idToken: String): User {
        return try {
            val response = userApi.getCurrentUser(idToken)
            if (response.code in 200..201) {
                response.data
            } else {
                User.EMPTY
            }
        } catch (e: Exception) {
            User.EMPTY
        }
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
