package com.example.domain.calculationLogic

import com.example.domain.DailyCalory
import com.example.domain.UserParams


private object CalculatorCalorie {
    fun calculatingCaloriesAndWater(params: UserParams) : DailyCalory {

        //Metric Mass to Kg
        val finalMass = when (params.metricMass) {
            MetriсMass.MassInLd -> params.mass / MetriсMass.MassInLd.metric
            MetriсMass.MassInKg -> params.mass
        }

        //Metric Height to Cm
        val finalHeight = when (params.metricHeight) {
            MetriсHeight.HeightInInch -> params.height * MetriсHeight.HeightInInch.metric
            MetriсHeight.HeightInCm -> params.height
        }


        //BMR
        val bmr = if (params.isMale) {
            10.0 * finalMass + 6.25 * finalHeight - 5.0 * params.age + 5.0
        } else {
            10.0 * finalMass + 6.25 * finalHeight - 5.0 * params.age - 161.0
        }

        //TDEE
        val tdee = bmr * params.activityLevel.factor

        //Result Calories
        val resultCalories = (tdee + params.target.factor)

        //PFC
        val proteins = when (params.target) {
            TargetInWeight.LoseWeight -> resultCalories * 0.30  // Lose weight
            TargetInWeight.MaintinWeight -> resultCalories * 0.25  // Maintain weight
            TargetInWeight.GainWeight -> resultCalories * 0.20  // Gain weight
        }
        val fats = when (params.target) {
            TargetInWeight.LoseWeight -> resultCalories * 0.25  // Lose weight
            TargetInWeight.MaintinWeight -> resultCalories * 0.30  // Maintain weight
            TargetInWeight.GainWeight -> resultCalories * 0.25  // Gain weight
        }
        val carbohydrates = when (params.target) {
            TargetInWeight.LoseWeight -> resultCalories * 0.45  // Lose weight
            TargetInWeight.MaintinWeight -> resultCalories * 0.45  // Maintain weight
            TargetInWeight.GainWeight -> resultCalories * 0.55  // Gain weight
        }

        //Water intake
        val water = when (params.activityLevel) {
            LifeActivityLevel.Minimum -> params.mass * 30.0  // Minimum
            LifeActivityLevel.Light -> params.mass * 30.0  // Light
            LifeActivityLevel.Moderate -> params.mass * 35.0  // Moderate
            LifeActivityLevel.Active -> params.mass * 40.0  // Active
            LifeActivityLevel.VeryActive -> params.mass * 45.0  // Very Active
        }
        return DailyCalory(
            finalMass = finalMass,
            finalHeight = finalHeight,
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
    fun recalculate(params: UserParams) : DailyCalory {
        return CalculatorCalorie.calculatingCaloriesAndWater(params)
    }
}