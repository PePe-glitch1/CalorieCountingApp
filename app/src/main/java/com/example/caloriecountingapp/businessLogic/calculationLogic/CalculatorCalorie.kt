package com.example.caloriecountingapp.businessLogic.calculationLogic

import com.example.caloriecountingapp.businessLogic.DailyResult
import com.example.caloriecountingapp.businessLogic.UserParams


private object CalculatorCalorie {
    fun сalculatingCaloriesAndWater(params: UserParams) : DailyResult {

        //BMR
        val bmr = if (params.isMale) {
            10.0 * params.mass + 6.25 * params.height - 5.0 * params.age + 5.0
        } else {
            10.0 * params.mass + 6.25 * params.height - 5.0 * params.age - 161.0
        }

        //TDEE
        val tdee = bmr * params.activityLevel.factor

        //Result Calories
        val resultCalories = (tdee + params.target.factor)

        //PFC
        val proteins = when (params.target) {
            TargetInWeight.LOSE_WEIGHT -> resultCalories * 0.30  // Lose weight
            TargetInWeight.MAINTAIN_WEIGHT -> resultCalories * 0.25  // Maintain weight
            TargetInWeight.GAIN_WEIGHT -> resultCalories * 0.20  // Gain weight
        }
        val fats = when (params.target) {
            TargetInWeight.LOSE_WEIGHT -> resultCalories * 0.25  // Lose weight
            TargetInWeight.MAINTAIN_WEIGHT -> resultCalories * 0.30  // Maintain weight
            TargetInWeight.GAIN_WEIGHT -> resultCalories * 0.25  // Gain weight
        }
        val carbohydrates = when (params.target) {
            TargetInWeight.LOSE_WEIGHT -> resultCalories * 0.45  // Lose weight
            TargetInWeight.MAINTAIN_WEIGHT -> resultCalories * 0.45  // Maintain weight
            TargetInWeight.GAIN_WEIGHT -> resultCalories * 0.55  // Gain weight
        }

        //Water intake
        val water = when (params.activityLevel) {
            LifeActivityLevel.MINIMUM -> params.mass * 30.0  // Minimum
            LifeActivityLevel.LIGHT -> params.mass * 30.0  // Light
            LifeActivityLevel.MODERATE -> params.mass * 35.0  // Moderate
            LifeActivityLevel.ACTIVE -> params.mass * 40.0  // Active
            LifeActivityLevel.VERY_ACTIVE -> params.mass * 45.0  // Very Active
        }
        return DailyResult(
            bmr = bmr,
            tdee = tdee,
            resultCalories = resultCalories.toInt(),
            proteins = proteins / 4,
            fats = fats / 9,
            carbohydrates = carbohydrates / 4,
            water = water,
        )
    }
}

class RecalculateCalorie () {
    operator fun invoke (params: UserParams): DailyResult {
        return CalculatorCalorie.сalculatingCaloriesAndWater(params)
    }
}