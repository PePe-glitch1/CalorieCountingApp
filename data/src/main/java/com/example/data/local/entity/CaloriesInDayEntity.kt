package com.example.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "calories_in_day",
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
data class CaloriesInDayEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "calories_in_day_id")
    val id: Long = 0,

    @ColumnInfo(name = "user_id")
    val userId: Long = 0,

    val date: LocalDate,
    val addTotalCalories: Double,
    val addTotalProteins: Double,
    val addTotalFats: Double,
    val addTotalCarbohydrates: Double,
)
