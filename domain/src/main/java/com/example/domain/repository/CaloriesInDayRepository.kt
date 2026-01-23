package com.example.domain.repository

import com.example.domain.models.CaloriesInDay
import java.time.LocalDate

interface CaloriesInDayRepository {

    suspend fun saveCaloriesInDay(params: CaloriesInDay): Long

    suspend fun getCaloriesInDayByUserIdAndDate(userId: Long, data: LocalDate): CaloriesInDay?

    suspend fun updateCaloriesInDay(params: CaloriesInDay)

    suspend fun deleteCaloriesInDayById(id: Long)

}