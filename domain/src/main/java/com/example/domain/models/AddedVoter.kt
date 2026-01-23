package com.example.domain.models

import java.time.LocalDateTime

data class AddedVoter(
    val id: Long = 0,
    val userId: Long = 0,
    val date: LocalDateTime = LocalDateTime.now(),
    val addVoterMl: Int,
)
