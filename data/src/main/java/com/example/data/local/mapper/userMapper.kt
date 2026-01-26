package com.example.data.local.mapper

import android.icu.util.Calendar
import com.example.data.local.entity.UserEntity
import com.example.domain.models.User
import java.time.LocalDate

fun UserEntity.toDomain(): User {

    val age = calculateAge(this.bornData)

    return User(
        id = this.id,
        username = this.username,
        email = this.email,
        bornData = this.bornData,
        age = age,
        notificationsEnabled = this.notificationsEnabled,
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        username = this.username,
        email = this.email,
        bornData = this.bornData,
        notificationsEnabled = this.notificationsEnabled,
    )
}

private fun calculateAge(bornDataYear: LocalDate): Int {
    val birth = Calendar.getInstance()
    birth.set(bornDataYear.year, bornDataYear.monthValue - 1, bornDataYear.dayOfMonth)
    val today = Calendar.getInstance()
    var age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR)
    if (today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
        age--
    }
    return age
}

fun List<UserEntity>.toDomain(): List<User> {
    return this.map { it.toDomain() }
}

fun List<User>.toEntity(): List<UserEntity> {
    return this.map { it.toEntity() }
}