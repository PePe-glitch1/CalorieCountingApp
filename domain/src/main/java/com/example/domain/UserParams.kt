package com.example.domain

import com.example.domain.calculationLogic.LifeActivityLevel
import com.example.domain.calculationLogic.MetriсHeight
import com.example.domain.calculationLogic.MetriсMass
import com.example.domain.calculationLogic.TargetInWeight
import java.time.LocalDateTime

class UserParams(
    val userParamsId: Long,
    val id: Long,
    isMale: Boolean,
    age: Int,
    metricMass: MetriсMass,
    mass: Double,
    metricHeight: MetriсHeight,
    height: Double,
    activityLevel: LifeActivityLevel,
    target: TargetInWeight,
) {
    var isMale: Boolean = isMale
        private set

    var age: Int = age
        private set

    var metricMass: MetriсMass = metricMass
        private set

    var mass: Double = mass
        private set

    var metricHeight: MetriсHeight = metricHeight
        private set

    var height: Double = height
        private set

    var activityLevel: LifeActivityLevel = activityLevel
        private set

    var target: TargetInWeight = target
        private set


    init {
        require(age in 1..130)                           { "INVALID_AGE" }
        require(mass in 1.0..300.0)                      { "INVALID_MASS" }
        require(height in 50.0..250.0)                   { "INVALID_HEIGHT" }
    }


    fun updateIsMale(newIsMail: Boolean) {
        isMale = newIsMail
    }

    fun updateAge(newAge: Int) {
        require(newAge in 1..130) { "INVALID_AGE" }
        age = newAge
    }

    fun updateMetricMass(newMetricMass: MetriсMass) {
        metricMass = newMetricMass
    }

    fun updateMass(newMass: Double) {
        require(newMass in 1.0..300.0) { "INVALID_MASS" }
        mass = newMass
    }

    fun updateMetricHeight(newMetricHight: MetriсHeight) {
        metricHeight = newMetricHight
    }

    fun updateHeight(newHeight: Double) {
        require(newHeight in 50.0..250.0) { "INVALID_HEIGHT" }
        height = newHeight
    }

    fun updateActivityLevel(newActivityLevel: LifeActivityLevel) {
        activityLevel = newActivityLevel
    }

    fun updateTarget(newTarget: TargetInWeight) {
        target = newTarget
    }
}