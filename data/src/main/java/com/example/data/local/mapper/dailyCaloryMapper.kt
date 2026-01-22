package com.example.data.local.mapper

import com.example.data.local.entity.DailyCaloryEntity
import com.example.domain.models.DailyCalory
import java.time.LocalDateTime

fun DailyCaloryEntity.toDomain(): DailyCalory {
    return DailyCalory(
        id = this.id,
        parentId = this.parentId,
        finalMass = this.finalMass,
        finalHeight = this.finalHeight,
        bmr = this.bmr,
        tdee = this.tdee,
        resultCalories = this.resultCalories,
        proteins = this.proteins,
        fats = this.fats,
        carbohydrates = this.carbohydrates,
        water = this.water,
    )
}

fun DailyCalory.toEntity(): DailyCaloryEntity {
    return DailyCaloryEntity(
        id = this.id,
        parentId = this.parentId,
        finalMass = this.finalMass,
        finalHeight = this.finalHeight,
        bmr = this.bmr,
        tdee = this.tdee,
        resultCalories = this.resultCalories,
        proteins = this.proteins,
        fats = this.fats,
        carbohydrates = this.carbohydrates,
        water = this.water,
        createdAt = LocalDateTime.now()
    )
}

fun List<DailyCaloryEntity>.toDomain(): List<DailyCalory> {
    return this.map { it.toDomain() }
}

fun List<DailyCalory>.toEntity(): List<DailyCaloryEntity> {
    return this.map { it.toEntity() }
}