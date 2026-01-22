package com.example.data.local.entity

import androidx.room.*
import com.example.domain.calculationLogic.LifeActivityLevel
import com.example.domain.calculationLogic.MetriсHeight
import com.example.domain.calculationLogic.MetriсMass
import com.example.domain.calculationLogic.TargetInWeight



@Entity(
    tableName = "users_params",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class UserParamsEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_params_id")
    val userParamsId: Long,

    @ColumnInfo(name = "user_id")
    val id: Long,

    val isMale: Boolean,
    val age: Int,
    val metricMass: MetriсMass,
    val mass: Double,
    val metricHeight: MetriсHeight,
    val height: Double,
    val activityLevel: LifeActivityLevel,
    val target: TargetInWeight,
)
