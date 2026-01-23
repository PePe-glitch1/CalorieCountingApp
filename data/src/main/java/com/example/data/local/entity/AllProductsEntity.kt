package com.example.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "all_products")
data class AllProductsEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "all_products_id")
    val id: Long = 0,

    val name: String,
    val caloriesIn1g: Double,
    val proteinsIn1g: Double,
    val fatsIn1g: Double,
    val carbohydratesIn1g: Double,
)
