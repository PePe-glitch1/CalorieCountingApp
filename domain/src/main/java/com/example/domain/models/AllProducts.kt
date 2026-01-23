package com.example.domain.models

data class AllProducts(
    val id: Long = 0,
    val name: String,
    val caloriesIn1g: Double,
    val proteinsIn1g: Double,
    val fatsIn1g: Double,
    val carbohydratesIn1g: Double,
)