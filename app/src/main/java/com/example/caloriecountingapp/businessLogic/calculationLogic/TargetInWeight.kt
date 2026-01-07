package com.example.caloriecountingapp.businessLogic.calculationLogic

sealed class TargetInWeight(val factor: Int) {

    object LoseWeight: TargetInWeight(-500)

    object MaintinWeight: TargetInWeight(0)

    object GainWeight: TargetInWeight(300)

}