package com.example.data.local.mapper

import com.example.data.local.entity.UserParamsEntity
import com.example.domain.UserParams

fun UserParamsEntity.toDomain(): UserParams {
    return UserParams(
        userParamsId = this.userParamsId,
        id = this.id,
        isMale = this.isMale,
        metricMass = this.metricMass,
        mass = this.mass,
        metricHeight = this.metricHeight,
        height = this.height,
        activityLevel = this.activityLevel,
        target = this.target
    )
}

fun UserParams.toEntity(): UserParamsEntity {
    return UserParamsEntity(
        userParamsId = this.userParamsId,
        id = this.id,
        isMale = this.isMale,
        metricMass = this.metricMass,
        mass = this.mass,
        metricHeight = this.metricHeight,
        height = this.height,
        activityLevel = this.activityLevel,
        target = this.target
    )
}

fun List<UserParamsEntity>.toDomain(): List<UserParams> {
    return this.map { it.toDomain() }
}

fun List<UserParams>.toEntity(): List<UserParamsEntity> {
    return this.map { it.toEntity() }
}

