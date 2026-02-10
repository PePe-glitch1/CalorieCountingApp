package com.example.domain.calculationLogic

sealed class LifeActivityLevel(val factor : Double) {

    object Minimum : LifeActivityLevel(1.2)

    object Light : LifeActivityLevel(1.375)

    object Middle : LifeActivityLevel(1.55)

    object Active : LifeActivityLevel(1.725)

    object VeryActive : LifeActivityLevel(1.9)

}