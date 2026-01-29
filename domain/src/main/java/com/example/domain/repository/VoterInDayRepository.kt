package com.example.domain.repository

import com.example.domain.models.VoterInDay

interface VoterInDayRepository {

    suspend fun addVoterInDay(voterInDay: VoterInDay): Long

    suspend fun getVoterInDayByUserIdAndDate(userId: Long): VoterInDay?

    suspend fun updateVoterInDay(voterInDay: VoterInDay)

    suspend fun deleteVoterInDayById(id: Long)

}