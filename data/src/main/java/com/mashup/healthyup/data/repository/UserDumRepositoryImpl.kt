package com.mashup.healthyup.data.repository

import com.mashup.healthyup.data.api.DumApi
import com.mashup.healthyup.data.request.Daangn
import com.helltabus.domain.entity.User
import com.helltabus.domain.repository.UserRepository
import javax.inject.Inject

class UserDumRepositoryImpl @Inject constructor(
    private val dumApi: DumApi
) : UserRepository {
    override suspend fun getUser(userId: Long): Result<User> {
        return try {
            val user = dumApi.getDumUser(userId.toString()).data
            if (user != null) {
                Result.Success(
                    user.toEntity()
                )
            } else {
                Result.Error(Exception("eeee"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun postUser(userId: Long): Result<User> {
        return try {
            val user = dumApi.postDumUser(Daangn(userId).invoke()).data
            if (user != null) {
                Result.Success(
                    user.toEntity()
                )
            } else {
                Result.Error(Exception("eeee"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
