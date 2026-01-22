package com.example.data.repository

import com.example.data.local.dao.UserParamsDao
import com.example.data.local.mapper.toDomain
import com.example.data.local.mapper.toEntity
import com.example.domain.models.UserParams
import com.example.domain.repository.UserParamsRepository

class UserParamsRepositoryImpl(
    private val userParamsDao: UserParamsDao
): UserParamsRepository {

    override suspend fun saveUserParams(params: UserParams): Long {
        val entity = params.toEntity()
        return if (params.id == 0L) {
            userParamsDao.insert(entity)
        } else {
            userParamsDao.update(entity)
            params.id
        }
    }

    override suspend fun getUserParamsById(userId: Long): UserParams? {
        val entity = userParamsDao.getUserParamsById(userId)
        return entity?.toDomain()
    }

    override suspend fun getUserParamsByUserId(userParamsId: Long): UserParams? {
        val entity = userParamsDao.getUserParamsById(userParamsId)
        return entity?.toDomain()
    }

    override suspend fun updateUserParams(params: UserParams) {
        val entity = params.toEntity()
        userParamsDao.update(entity)
    }

    override suspend fun deleteUserParamsById(userParamsId: Long) {
        userParamsDao.getUserParamsById(userParamsId)
    }

}