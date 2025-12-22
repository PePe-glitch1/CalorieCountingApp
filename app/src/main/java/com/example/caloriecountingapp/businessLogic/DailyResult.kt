package com.example.caloriecountingapp.businessLogic

import kotlin.TODO

data class DailyResult(
    val userId : String,
    val bmr: Double,
    val tdee: Double,
    val resultCalories: Int,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double,
    val water: Double,
)