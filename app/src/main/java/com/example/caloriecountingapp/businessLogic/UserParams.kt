package com.example.caloriecountingapp.businessLogic

import com.example.caloriecountingapp.businessLogic.calculationLogic.LifeActivityLevel
import com.example.caloriecountingapp.businessLogic.calculationLogic.TargetInWeight

data class UserParams(
    val isMale: Boolean,
    val age: Int,
    val mass: Double,
    val height: Int,
    val activityLevel: LifeActivityLevel,
    val target: TargetInWeight,
) {
    init {
        require(age in 1..130) { "INVALID_AGE" }
        require(mass in 1.0..300.0) { "INVALID_MASS" }
        require(height in 50..250) { "INVALID_HEIGHT" }
    }
}