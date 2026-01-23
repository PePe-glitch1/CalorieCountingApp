package com.example.domain.models

import com.example.domain.calculationLogic.LifeActivityLevel
import com.example.domain.calculationLogic.MetriсHeight
import com.example.domain.calculationLogic.MetriсMass
import com.example.domain.calculationLogic.TargetInWeight

data class UserParams(
    val id: Long = 0,
    val userId: Long = 0,
    val isMale: Boolean,
    val metricMass: MetriсMass,
    val mass: Double,
    val metricHeight: MetriсHeight,
    val height: Double,
    val activityLevel: LifeActivityLevel,
    val target: TargetInWeight,
) {
    init {
        require(mass in 1.0..300.0) { "INVALID_MASS" }
        require(height in 50.0..250.0) { "INVALID_HEIGHT" }
    }
}