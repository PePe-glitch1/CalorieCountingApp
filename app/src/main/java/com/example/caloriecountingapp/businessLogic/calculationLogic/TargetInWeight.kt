package com.example.caloriecountingapp.businessLogic.calculationLogic

enum class TargetInWeight(val factor: Int) {
    LOSE_WEIGHT(-500),
    MAINTAIN_WEIGHT(0),
    GAIN_WEIGHT(300),
}