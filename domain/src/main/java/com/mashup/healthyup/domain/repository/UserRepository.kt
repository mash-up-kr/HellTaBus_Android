/*
 * Created by Leo on 2021. 09. 26 ..
 */
package com.mashup.healthyup.domain.repository

import com.mashup.healthyup.domain.Result
import com.mashup.healthyup.domain.entity.User

interface UserRepository : Repository {
    suspend fun register()
    suspend fun signIn()
    suspend fun getCurrentUser(): Result<User>
    suspend fun patchBaseInformation(user: User): Result<User>
    suspend fun update(user: User): Result<User>
}
