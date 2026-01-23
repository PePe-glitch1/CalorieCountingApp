package com.example.data.local.mapper

import com.example.data.local.entity.AddedVoterEntity
import com.example.domain.models.AddedVoter

fun AddedVoterEntity.toDomain(): AddedVoter {
    return AddedVoter(
        id = this.id,
        userId = this.userId,
        date = this.date,
        addVoterMl = this.addVoterMl,
    )
}

fun AddedVoter.toEntity(): AddedVoterEntity {
    return AddedVoterEntity(
        id = this.id,
        userId = this.userId,
        date = this.date,
        addVoterMl = this.addVoterMl,
    )
}

fun List<AddedVoterEntity>.toDomain(): List<AddedVoter> {
    return this.map { it.toDomain() }
}

fun List<AddedVoter>.toEntity(): List<AddedVoterEntity> {
    return this.map { it.toEntity() }
}