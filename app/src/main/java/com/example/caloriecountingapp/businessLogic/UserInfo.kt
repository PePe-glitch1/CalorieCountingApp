package com.example.caloriecountingapp.businessLogic

import android.icu.util.LocaleData


data class UserInfo(
    val userId : String,
    val dataBorn: LocaleData,
    val mail: String,
    val isPremium: Boolean,
)
