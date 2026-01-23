package com.example.data.local.mapper

import com.example.data.local.entity.VoterInDayEntity
import com.example.domain.models.VoterInDay

fun VoterInDayEntity.toDomain(): VoterInDay {
    return VoterInDay(
        id = this.id,
        userId = this.userId,
        date = this.date,
        votersMl = this.votersMl,
    )
}

fun VoterInDay.toEntity(): VoterInDayEntity {
    return VoterInDayEntity(
        id = this.id,
        userId = this.userId,
        date = this.date,
        votersMl = this.votersMl,
    )
}

fun List<VoterInDayEntity>.toDomain(): List<VoterInDay> {
    return this.map { it.toDomain() }
}

fun List<VoterInDay>.toEntity(): List<VoterInDayEntity> {
    return this.map { it.toEntity() }
}