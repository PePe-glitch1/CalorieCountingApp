package com.example.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.calculationLogic.LifeActivityLevel
import com.example.domain.calculationLogic.TargetInWeight

@Entity(tableName = "users_info")
data class Users(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val id: Long,

    val name: String,
    val mail: String,
    val isMale: Boolean,
    val bornData: String,
    val age: Int,
    val metricMass: String,
    val mass: Double,
    val metricHeight: String,
    val height: Double,
    val activityLevel: LifeActivityLevel,
    val target: TargetInWeight,
    val createdAt: Long = System.currentTimeMillis(),
)
