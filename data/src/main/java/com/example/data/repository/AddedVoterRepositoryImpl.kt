package com.example.data.repository

import com.example.data.local.dao.AddedVoterDao
import com.example.data.local.mapper.toDomain
import com.example.data.local.mapper.toEntity
import com.example.domain.models.AddedVoter
import com.example.domain.repository.AddedVoterRepository
import java.time.LocalDateTime

class AddedVoterRepositoryImpl(
    private val addedVoterDao: AddedVoterDao
): AddedVoterRepository {

    override suspend fun saveAddedVoter(params: AddedVoter): Long {
        val entity = params.toEntity()
        addedVoterDao.insert(entity)
        return params.id
    }

    override suspend fun getAllAddedVoterByUserIdAndDate(
        userId: Long,
        date: LocalDateTime
    ): List<AddedVoter>? {
        val entities = addedVoterDao.getAddedVoterByUserIdAndDate(userId, date)
        return entities?.map { it.toDomain() }
    }

    override suspend fun getAllAddedVoterEntityByUserId(userId: Long): List<AddedVoter>? {
        val entities = addedVoterDao.getAddedVoterByUserId(userId)
        return entities?.map { it.toDomain() }
    }

    override suspend fun getAddedVoterEntityById(id: Long): AddedVoter? {
        val entity = addedVoterDao.getAddedVoterById(id)
        return entity?.toDomain()
    }

    override suspend fun deleteAddedVoterById(id: Long) {
        addedVoterDao.deleteById(id)
    }

    override suspend fun getVotersInDateRange(
        userId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<AddedVoter> {
        return addedVoterDao.getAddedVoterByUserIdInRange(userId, from, to)
            .map { it.toDomain() }
    }
}