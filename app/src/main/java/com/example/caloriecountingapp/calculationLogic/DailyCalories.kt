package com.example.caloriecountingapp.calculationLogic

class DailyCalories(
    age: Int,
    massKg: Double,
    heightCm: Int,
    activityLevel: Int,
    target: Int,
) {

    companion object {
        const val MINIMUM = 1.2
        const val LIGHT = 1.375
        const val MODERATE = 1.55
        const val ACTIVE = 1.725
        const val VERY_ACTIVE = 1.9
    }

    private var age: Int = 0
    private var massKg: Double = 0.0
    private var heightCm: Int = 0
    private var activityLevel: Int = 1
    private var target: Int = 0

    var bmr: Double = 0.0
        private set
    var tdee: Double = 0.0
        private set
    var resultCalories: Int = 0
        private set
    var proteins: Double = 0.0
        private set
    var fats: Double = 0.0
        private set
    var carbohydrates: Double = 0.0
        private set
    var water: Double = 0.0
        private set

    init {
        setAge(age)
        setMassKg(massKg)
        setHeightCm(heightCm)
        setActivity(activityLevel)
        setTarget(target)
    }

    fun setAge(value: Int) {
        require(value in 1..130) { "Age must be 1..130" }
        age = value
    }

    fun setMassKg(value: Double) {
        require(value in 1.0..300.0) { "Mass must be 1..300 kg" }
        massKg = value
    }

    fun setHeightCm(value: Int) {
        require(value in 50..250) { "Height must be 50..250 cm" }
        heightCm = value
    }

    fun setActivity(value: Int) {
        require(value in 1..5) { "Activity must be 1..5" }
        activityLevel = value
    }

    fun setTarget(value: Int) {
        require(value in 1..3) { "Target must be 1..3" }
        target = value
    }

    fun calcBmr(isMale: Boolean): Double {
        bmr = if (isMale) {
            10.0 * massKg + 6.25 * heightCm - 5.0 * age + 5.0
        } else {
            10.0 * massKg + 6.25 * heightCm - 5.0 * age - 161.0
        }
        return bmr
    }

    fun calcTdee(): Double {
        require(bmr > 0.0) { "Call calcBmr(isMale) first" }
        val factor = when (activityLevel) {
            1 -> MINIMUM
            2 -> LIGHT
            3 -> MODERATE
            4 -> ACTIVE
            5 -> VERY_ACTIVE
            else -> MINIMUM
        }
        tdee = bmr * factor
        return tdee
    }

    fun calcResaltCalories(): Int {
        require(tdee > 0.0) { "Call calcTdee() first" }
        resultCalories = when (target) {
            1 -> (tdee - 500).toInt()  // Lose weight
            2 -> tdee.toInt()          // Maintain weight
            3 -> (tdee + 300).toInt()  // Gain weight
            else -> tdee.toInt()
        }
        return resultCalories
    }

    fun calcMacros(): Triple<Double, Double, Double> {
        require(resultCalories > 0) { "Call calcResaltCalories() first" }
        proteins = when (target) {
            1 -> resultCalories * 0.30  // Lose weight
            2 -> resultCalories * 0.25  // Maintain weight
            3 -> resultCalories * 0.20  // Gain weight
            else -> resultCalories * 0.25
        }
        fats = when (target) {
            1 -> resultCalories * 0.25  // Lose weight
            2 -> resultCalories * 0.30  // Maintain weight
            3 -> resultCalories * 0.25  // Gain weight
            else -> resultCalories * 0.30
        }
        carbohydrates = when (target) {
            1 -> resultCalories * 0.45  // Lose weight
            2 -> resultCalories * 0.45  // Maintain weight
            3 -> resultCalories * 0.55  // Gain weight
            else -> resultCalories * 0.45
        }
        return Triple(proteins/4, fats/9, carbohydrates/4)
    }

    fun waterBalance(massKg: Double): Double {
        water = when (activityLevel) {
            1 -> massKg * 30.0  // Minimum
            2 -> massKg * 30.0  // Light
            3 -> massKg * 35.0  // Moderate
            4 -> massKg * 40.0  // Active
            5 -> massKg * 45.0  // Very Active
            else -> massKg * 30.0
        }
        return water
    }
}
