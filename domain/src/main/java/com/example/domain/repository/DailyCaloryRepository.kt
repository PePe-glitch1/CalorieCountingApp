package com.example.domain.repository

import com.example.domain.models.DailyCalory

interface DailyCaloryRepository {

    suspend fun saveDailyCalory(params: DailyCalory): Long

    suspend fun getDailyCaloryById(id: Long): DailyCalory?

    suspend fun gitDailyCaloryByParentId(parentId: Long): DailyCalory?

    suspend fun updateDailyCalory(params: DailyCalory)

    suspend fun deleteDailyCaloryById(id: Long)


}