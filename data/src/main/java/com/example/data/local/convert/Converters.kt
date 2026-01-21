package com.example.data.local.convert

import androidx.room.TypeConverter
import com.example.domain.calculationLogic.LifeActivityLevel
import com.example.domain.calculationLogic.MetriсMass
import com.example.domain.calculationLogic.MetriсHeight
import com.example.domain.calculationLogic.TargetInWeight


class Converters {

    @TypeConverter
    fun fromSealedClassLifeActivityLevelToString(value: LifeActivityLevel): String {
        return when (value) {
            is LifeActivityLevel.Minimum   -> "Minimum"
            is LifeActivityLevel.Light     -> "Light"
            is LifeActivityLevel.Moderate  -> "Moderate"
            is LifeActivityLevel.Active    -> "Active"
            is LifeActivityLevel.VeryActive-> "VeryActive"
        }
    }

    @TypeConverter
    fun toSealedClassLifeActivityLevelFromString(value: String): LifeActivityLevel {
        return when (value) {
            "Minimum" -> LifeActivityLevel.Minimum
            "Light" -> LifeActivityLevel.Light
            "Moderate" -> LifeActivityLevel.Moderate
            "Active" -> LifeActivityLevel.Active
            "VeryActive" -> LifeActivityLevel.VeryActive
            else -> throw IllegalArgumentException("Unknown LifeActivityLevel: $value")
        }
    }

    @TypeConverter
    fun fromSealedClassTargethWeightToString(value: TargetInWeight): String {
        return when (value) {
            is TargetInWeight.LoseWeight    -> "LoseWeight"
            is TargetInWeight.MaintinWeight  -> "MaintinWeight"
            is TargetInWeight.GainWeight     -> "GainWeight"
        }
    }

    @TypeConverter
    fun toSealedClassTargetWeightFromString(value: String): TargetInWeight {
        return when (value) {
            "LoseWeight" -> TargetInWeight.LoseWeight
            "MaintinWeight" -> TargetInWeight.MaintinWeight
            "GainWeight" -> TargetInWeight.GainWeight
            else -> throw IllegalArgumentException("Unknown TargetInWeight: $value")
        }
    }

    @TypeConverter
    fun fromSealedClassMetricMassToString(value: MetriсMass): String {
        return when (value) {
            is MetriсMass.MassInLd -> "MassInLd"
            is MetriсMass.MassInKg -> "MassInKg"
        }
    }

    @TypeConverter
    fun toSealedClassMetricMassFromString(value: String): MetriсMass {
        return when (value) {
            "MassInLd" -> MetriсMass.MassInLd
            "MassInKg" -> MetriсMass.MassInKg
            else -> throw IllegalArgumentException("Unknown MetriсMass: $value")
        }
    }

    @TypeConverter
    fun fromSealedClassMetricHeightToString(value: MetriсHeight): String {
        return when (value) {
            is MetriсHeight.HeightInInch -> "HeightInInch"
            is MetriсHeight.HeightInCm -> "HeightInCm"
        }
    }

    @TypeConverter
    fun toSealedClassMetricHeightFromString(value: String): MetriсHeight {
        return when (value) {
            "HeightInInch" -> MetriсHeight.HeightInInch
            "HeightInCm" -> MetriсHeight.HeightInCm
            else -> throw IllegalArgumentException("Unknown MetriсHeight: $value")
        }
    }
}