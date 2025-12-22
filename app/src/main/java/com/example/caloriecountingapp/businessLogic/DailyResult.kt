package com.example.caloriecountingapp.businessLogic

data class DailyResult(
    val bmr: Double,
    val tdee: Double,
    val resultCalories: Int,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double,
    val water: Double,
)