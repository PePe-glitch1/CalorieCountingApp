package com.example.data.repository

import com.example.data.local.dao.CaloriesInDayDao
import com.example.data.local.mapper.toEntity
import com.example.data.local.mapper.toDomain
import com.example.domain.models.CaloriesInDay
import com.example.domain.repository.CaloriesInDayRepository
import java.time.LocalDate

class CaloriesInDayRepositoryImpl(
    private val caloriesInDayDao: CaloriesInDayDao
): CaloriesInDayRepository {

    override suspend fun saveCaloriesInDay(params: CaloriesInDay): Long {
        val entity = params.toEntity()
        return if (params.id == 0L) {
            caloriesInDayDao.insert(entity)
        } else {
            caloriesInDayDao.update(entity)
            params.id
        }
    }

    override suspend fun getCaloriesInDayByUserIdAndDate( userId: Long, ): CaloriesInDay? {
        val entity = caloriesInDayDao.getCaloriesInDayByUserIdAndDate(userId)
        return entity?.toDomain()
    }

    override suspend fun updateCaloriesInDay(params: CaloriesInDay) {
        val entity = params.toEntity()
        caloriesInDayDao.update(entity)
    }

    override suspend fun deleteCaloriesInDayById(id: Long) {
        caloriesInDayDao.deleteById(id)
    }


}