package com.example.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity( tableName = "users" )
data class User(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val id: Long,

    val username: String,
    val email: String,
    val bornData: LocalDate,
    val dataAdd: LocalDateTime = LocalDateTime.now()
)
