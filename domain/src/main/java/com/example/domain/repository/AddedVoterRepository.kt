package com.example.domain.repository

import com.example.domain.models.AddedVoter
import java.time.LocalDateTime

interface AddedVoterRepository {

    suspend fun saveAddedVoter(params: AddedVoter): Long

    suspend fun getAllAddedVoterByUserIdAndDate(userId: Long, date: LocalDateTime): List<AddedVoter>?

    suspend fun getAllAddedVoterEntityByUserId(userId: Long): List<AddedVoter>?

    suspend fun getAddedVoterEntityById(id: Long): AddedVoter?

    suspend fun deleteAddedVoterById(id: Long)

}