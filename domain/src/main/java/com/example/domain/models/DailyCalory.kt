package com.example.domain.models


data class DailyCalory(
    val id: Long = 0,
    val parentId: Long = 0,
    val finalMass: Double,
    val finalHeight: Double,
    val bmr: Double,
    val tdee: Double,
    val resultCalories: Int,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double,
    val water: Double,
) {
    init {
        require(bmr > 0.0)           { "INVALID_BMR" }
        require(tdee > 0.0)          { "INVALID_TDEE" }
        require(resultCalories > 0)  { "INVALID_RESULTCALORIES" }
        require(proteins >= 0)       { "INVALID_PROTEINS" }
        require(fats >= 0)           { "INVALID_FATS" }
        require(carbohydrates >= 0)  { "INVALID_CARBOHYDRATES" }
        require(water >= 0)          { "INVALID_WATER" }
    }
}