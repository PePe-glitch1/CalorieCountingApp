package com.example.domain.models

import java.time.LocalDate

data class User(
    val id: Long = 0,
    val username: String,
    val email: String,
    val bornData: LocalDate,
    val age: Int = 0,
    val notificationsEnabled: Boolean = true,
)
