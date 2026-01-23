package com.example.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "added_products",
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
data class AddedProductsEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "added_products_id")
    val id: Long = 0,

    @ColumnInfo(name = "user_id")
    val userId: Long = 0,

    val date: LocalDateTime,
    val name: String,
    val mass: Double,
    val calories: Double,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double,
)
