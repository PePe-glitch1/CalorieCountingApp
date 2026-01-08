package com.example.domain

import com.example.domain.calculationLogic.LifeActivityLevel
import com.example.domain.calculationLogic.MetriсHeight
import com.example.domain.calculationLogic.MetriсMass
import com.example.domain.calculationLogic.TargetInWeight

class UserInfo(
    val userId: String,
    name: String,
    mail: String,
    isMale: Boolean,
    bornData: String,
    age: Int,
    metricMass: MetriсMass,
    mass: Double,
    metricHeight: MetriсHeight,
    height: Double,
    activityLevel: LifeActivityLevel,
    target: TargetInWeight,
) {
    var name: String = name
        private set

    var mail: String = mail
        private set

    var isMale: Boolean = isMale
        private set

    var bornData: String = bornData
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
        require(name.isNotBlank())                       { "INVALID_NAME" }
        require(mail.isNotBlank() && mail.contains("@")) { "INVALID_MAIL" }
        require(bornData.isNotEmpty())                   { "INVALID_BORN_DATA" }
        require(age in 1..130)                           { "INVALID_AGE" }
        require(mass in 1.0..300.0)                      { "INVALID_MASS" }
        require(height in 50.0..250.0)                   { "INVALID_HEIGHT" }
    }


    fun updateName(newName: String) {
        require(newName.isNotBlank()) { "INVALID_NAME" }
        name = newName
    }

    fun updateMail(newMail: String) {
        require(newMail.isNotBlank() && newMail.contains("@")) { "INVALID_MAIL" }
        mail = newMail
    }

    fun updateIsMale(newIsMail: Boolean) {
        isMale = newIsMail
    }

    fun updateBornData(newBornData: String) {
        require(newBornData.isNotEmpty()) { "INVALID_BORN_DATA" }
        bornData = newBornData
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