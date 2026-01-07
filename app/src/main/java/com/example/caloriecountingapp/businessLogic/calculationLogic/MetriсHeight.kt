package com.example.caloriecountingapp.businessLogic.calculationLogic

sealed class MetriсHeight(params: Double) {

    object HeightInCm : MetriсHeight(1.0)

    object HeightInInch : MetriсHeight(2.54)

}