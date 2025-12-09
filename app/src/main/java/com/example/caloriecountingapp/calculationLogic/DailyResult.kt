package com.example.caloriecountingapp.calculationLogic

data class DailyResult(
    val bmr: Double,
    val tdee: Double,
    val resultCalories: Int,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double,
    val water: Double,
)
