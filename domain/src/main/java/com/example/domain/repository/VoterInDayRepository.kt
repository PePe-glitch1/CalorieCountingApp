package com.example.domain.repository

import com.example.domain.models.VoterInDay
import java.time.LocalDate

interface VoterInDayRepository {

    suspend fun addVoterInDay(voterInDay: VoterInDay): Long

    suspend fun getVoterInDayByUserIdAndDate(userId: Long, date: LocalDate): VoterInDay?

    suspend fun updateVoterInDay(voterInDay: VoterInDay)

    suspend fun deleteVoterInDayById(id: Long)

}