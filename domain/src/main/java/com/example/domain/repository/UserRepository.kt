package com.example.domain.repository

import com.example.domain.models.User

interface UserRepository {

    suspend fun saveUser(params: User): Long

    suspend fun getUserById(userId: Long): User?

    suspend fun updateUser(params: User)

    suspend fun deleteUserById(userId: Long)

}