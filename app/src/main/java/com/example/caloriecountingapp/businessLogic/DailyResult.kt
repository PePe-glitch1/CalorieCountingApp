package com.example.caloriecountingapp.businessLogic


data class DailyResult(
    val userId : String,
    val bmr: Double,
    val tdee: Double,
    val resultCalories: Int,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double,
    val water: Double,
) {
    init {
        require(userId.isNotEmpty()) { "INVALID_USER_ID" }
        require(bmr > 0.0)           { "INVALID_BMR" }
        require(tdee > 0.0)          { "INVALID_TDEE" }
        require(resultCalories > 0)  { "INVALID_RESULTCALORIES" }
        require(proteins >= 0)       { "INVALID_PROTEINS" }
        require(fats >= 0)           { "INVALID_FATS" }
        require(carbohydrates >= 0)  { "INVALID_CARBOHYDRATES" }
        require(water >= 0)          { "INVALID_WATER" }
    }
}