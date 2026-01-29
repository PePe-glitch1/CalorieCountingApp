package com.example.data.repository

import com.example.data.local.dao.VoterInDayDao
import com.example.data.local.mapper.toDomain
import com.example.data.local.mapper.toEntity
import com.example.domain.models.VoterInDay
import com.example.domain.repository.VoterInDayRepository

class VoterInDayRepositoryImpl(
    private val voterInDayDao: VoterInDayDao
): VoterInDayRepository {

    override suspend fun addVoterInDay(voterInDay: VoterInDay): Long {
        val entity = voterInDay.toEntity()
        return if (
            voterInDay.id == 0L
        ) {
            voterInDayDao.insert(entity)
        } else {
            voterInDayDao.update(entity)
            voterInDay.id
        }
    }

    override suspend fun getVoterInDayByUserIdAndDate( userId: Long, ): VoterInDay? {
        val entity = voterInDayDao.getVoterInDayByUserIdAndDate(userId)
        return entity?.toDomain()
    }

    override suspend fun updateVoterInDay(voterInDay: VoterInDay) {
        val entity = voterInDay.toEntity()
        voterInDayDao.update(entity)
    }

    override suspend fun deleteVoterInDayById(id: Long) {
        voterInDayDao.deleteById(id)
    }
}