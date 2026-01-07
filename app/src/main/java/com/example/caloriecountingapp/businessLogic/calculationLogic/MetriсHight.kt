package com.example.caloriecountingapp.businessLogic.calculationLogic

sealed class MetriсHight(params: Double) {

    object HightInCm : MetriсHight(1.0)

    object HightInInch : MetriсHight(2.54)

}