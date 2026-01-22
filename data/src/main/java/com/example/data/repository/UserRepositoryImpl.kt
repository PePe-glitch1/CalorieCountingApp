package com.example.data.repository

import com.example.data.local.dao.UserDao
import com.example.data.local.mapper.toDomain
import com.example.data.local.mapper.toEntity
import com.example.domain.models.User
import com.example.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao
): UserRepository {

    override suspend fun saveUser(params: User): Long {
        val entity = params.toEntity()
        return if (params.id == 0L) {
            userDao.insert(entity)
        } else {
            userDao.update(entity)
            params.id
        }
    }

    override suspend fun getUserById(userId: Long): User? {
        val entity = userDao.getUserById(userId)
        return entity?.toDomain()
    }

    override suspend fun updateUser(params: User) {
        val entity = params.toEntity()
        userDao.update(entity)
    }

    override suspend fun deleteUserById(userId: Long) {
        userDao.deleteById(userId)
    }

}