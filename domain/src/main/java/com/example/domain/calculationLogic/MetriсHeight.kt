package com.example.domain.calculationLogic

sealed class MetriсHeight(val metric: Double) {

    object HeightInCm : MetriсHeight(1.0)

    object HeightInInch : MetriсHeight(2.54)

}