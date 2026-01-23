package com.example.domain.models

import java.time.LocalDate

data class VoterInDay(
    val id: Long = 0,
    val userId: Long = 0,
    val date: LocalDate = LocalDate.now(),
    val votersMl: Int,
) {
    init {
        require(votersMl >= 0) { "INVALID_VOTERS_ML" }
    }
}
