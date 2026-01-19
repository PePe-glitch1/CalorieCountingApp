package com.example.data.local.entity

import androidx.room.*

@Entity(
    tableName = "daily_calory",
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["id"],
            childColumns = ["parent_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class DailyCalory(
    @ColumnInfo(name = "parent_id")
    val parentId: Long,

    val finalMass: Double,
    val finalHeight: Double,
    val bmr: Double,
    val tdee: Double,
    val resultCalories: Int,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double,
    val water: Double,

)
