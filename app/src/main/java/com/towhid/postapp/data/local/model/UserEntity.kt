package com.towhid.postapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val email: String,
    val passwordHash: String
)