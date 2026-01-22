package com.example.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithUserParams(
    @Embedded
    val user: UserEntity,

    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_id"
    )

    val userParams: UserParamsEntity?
)
