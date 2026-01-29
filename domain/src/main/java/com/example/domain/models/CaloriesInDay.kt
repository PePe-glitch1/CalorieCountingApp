package com.example.domain.models

import java.time.LocalDate

data class CaloriesInDay(
    val id: Long = 0,
    val userId: Long = 0,
    val date: LocalDate = LocalDate.now(),
    val addTotalCalories: Double,
    val addTotalProteins: Double,
    val addTotalFats: Double,
    val addTotalCarbohydrates: Double,
) {
    init {
        require(addTotalCalories >= 0)          { "INVALID_CALORIES" }
        require(addTotalProteins >= 0)          { "INVALID_PROTEINS" }
        require(addTotalFats >= 0)              { "INVALID_FATS" }
        require(addTotalCarbohydrates >= 0)     { "INVALID_CARBOHYDRATES" }
    }
}
