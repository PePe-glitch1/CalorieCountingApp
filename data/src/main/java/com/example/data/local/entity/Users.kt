package com.example.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.calculationLogic.LifeActivityLevel
import com.example.domain.calculationLogic.MetriсHeight
import com.example.domain.calculationLogic.MetriсMass
import com.example.domain.calculationLogic.TargetInWeight
import java.time.LocalDate
import java.time.LocalDateTime


@Entity(tableName = "users_info")
data class Users(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val id: Long,

    val name: String,
    val mail: String,
    val isMale: Boolean,
    val bornData: LocalDate,
    val age: Int,
    val metricMass: MetriсMass,
    val mass: Double,
    val metricHeight: MetriсHeight,
    val height: Double,
    val activityLevel: LifeActivityLevel,
    val target: TargetInWeight,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
