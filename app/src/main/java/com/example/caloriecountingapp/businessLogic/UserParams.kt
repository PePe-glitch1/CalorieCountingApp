package com.example.caloriecountingapp.businessLogic

import com.example.caloriecountingapp.businessLogic.calculationLogic.LifeActivityLevel
import com.example.caloriecountingapp.businessLogic.calculationLogic.TargetInWeight

data class UserParams(
    val userId : String,
    val isMale: Boolean,
    val age: Int,
    val massKg: Double,
    val heightCm: Int,
    val activityLevel: LifeActivityLevel,
    val target: TargetInWeight,
) {
    init {
        require(age in 1..130)         { "INVALID_AGE" }
        require(massKg in 1.0..300.0)  { "INVALID_MASS" }
        require(heightCm in 50..250)   { "INVALID_HEIGHT" }
    }
}