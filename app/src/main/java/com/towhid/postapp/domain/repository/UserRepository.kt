package com.towhid.postapp.domain.repository

import com.towhid.postapp.domain.model.User

interface UserRepository {
    suspend fun register(user: User)
    suspend fun findByEmail(email: String): Boolean?
}