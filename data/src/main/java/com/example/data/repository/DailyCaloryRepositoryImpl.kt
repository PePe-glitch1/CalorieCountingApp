package com.example.data.repository

import com.example.data.local.dao.DailyCaloryDao
import com.example.data.local.mapper.toDomain
import com.example.data.local.mapper.toEntity
import com.example.domain.models.DailyCalory
import com.example.domain.repository.DailyCaloryRepository

class DailyCaloryRepositoryImpl(
    private val dailyCaloryDao: DailyCaloryDao
): DailyCaloryRepository {

    override suspend fun saveDailyCalory(params: DailyCalory): Long {
        val entity = params.toEntity()
        return if (params.id == 0L) {
            dailyCaloryDao.insert(entity)
        } else {
            dailyCaloryDao.update(entity)
            params.id
        }
    }

    override suspend fun getDailyCaloryById(id: Long): DailyCalory? {
        val entity = dailyCaloryDao.getDailyCaloryById(id)
        return entity?.toDomain()
    }

    override suspend fun gitDailyCaloryByParentId(parentId: Long): DailyCalory? {
        val entity = dailyCaloryDao.getDailyCaloryByParentId(parentId)
        return entity?.toDomain()
    }

    override suspend fun updateDailyCalory(params: DailyCalory) {
        val entity = params.toEntity()
        dailyCaloryDao.update(entity)
    }

    override suspend fun deleteDailyCaloryById(id: Long) {
        dailyCaloryDao.delete(id)
    }
}