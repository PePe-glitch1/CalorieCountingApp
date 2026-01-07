package com.example.caloriecountingapp.businessLogic.calculationLogic

sealed class MetriсMass(params: Double) {

    object MassInKg : MetriсMass(1.0)

    object MassInLd : MetriсMass(2.20462)

}