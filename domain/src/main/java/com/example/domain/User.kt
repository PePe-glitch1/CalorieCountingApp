package com.example.domain

import java.time.LocalDate

data class User(
    val id: Long,
    val username: String,
    val email: String,
    val bornData: LocalDate,
)
