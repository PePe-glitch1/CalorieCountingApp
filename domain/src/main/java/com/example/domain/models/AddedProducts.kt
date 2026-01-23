package com.example.domain.models

import java.time.LocalDateTime

data class AddedProducts(
    val id: Long = 0,
    val userId: Long = 0,
    val data: LocalDateTime = LocalDateTime.now(),
    val name: String,
    val mass: Double,
    val calories: Double,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double,
) {
    init {
        require(mass > 0)            { "INVALID_MASS" }
        require(calories >= 0)       { "INVALID_CALORIES" }
        require(proteins >= 0)       { "INVALID_PROTEINS" }
        require(fats >= 0)           { "INVALID_FATS" }
        require(carbohydrates >= 0)  { "INVALID_CARBOHYDRATES" }
    }
}
