package com.example.domain.repository

import com.example.domain.models.UserParams

interface UserParamsRepository {

    suspend fun saveUserParams (params: UserParams): Long

    suspend fun getUserParamsById(userId: Long): UserParams?

    suspend fun getUserParamsByUserId(userParamsId: Long): UserParams?

    suspend fun updateUserParams(params: UserParams)

    suspend fun deleteUserParamsById(userParamsId: Long)

}