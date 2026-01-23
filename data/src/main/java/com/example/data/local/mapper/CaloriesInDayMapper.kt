package com.example.data.local.mapper

import com.example.data.local.entity.CaloriesInDayEntity
import com.example.domain.models.CaloriesInDay

fun CaloriesInDayEntity.toDomain(): CaloriesInDay {
    return CaloriesInDay(
        id = this.id,
        userId = this.userId,
        date = this.date,
        addTotalCalories = this.addTotalCalories,
        addTotalProteins = this.addTotalProteins,
        addTotalFats = this.addTotalFats,
        addTotalCarbohydrates = this.addTotalCarbohydrates,
    )
}

fun CaloriesInDay.toEntity(): CaloriesInDayEntity {
    return CaloriesInDayEntity(
        id = this.id,
        userId = this.userId,
        date = this.date,
        addTotalCalories = this.addTotalCalories,
        addTotalProteins = this.addTotalProteins,
        addTotalFats = this.addTotalFats,
        addTotalCarbohydrates = this.addTotalCarbohydrates,
    )
}

fun List<CaloriesInDayEntity>.toDomain(): List<CaloriesInDay> {
    return this.map { it.toDomain() }
}

fun List<CaloriesInDay>.toEntity(): List<CaloriesInDayEntity> {
    return this.map { it.toEntity() }
}