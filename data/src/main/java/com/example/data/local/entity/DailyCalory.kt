package com.example.data.local.entity

import androidx.room.*

@Entity(
    tableName = "daily_calory",
    foreignKeys = [
        ForeignKey(
            entity = UserParams::class,
            parentColumns = ["user_params_id"],
            childColumns = ["parent_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class DailyCalory(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "daily_calory_id")
    val id: Long = 0,

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
    var createdAt: Long = System.currentTimeMillis(),
)
