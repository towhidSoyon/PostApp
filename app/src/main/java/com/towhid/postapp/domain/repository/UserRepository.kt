package com.towhid.postapp.domain.repository

import com.towhid.postapp.domain.model.User
import com.towhid.postapp.utils.LoginResult

interface UserRepository {
    suspend fun register(user: User)
    suspend fun findByEmail(email: String, password: String): LoginResult?
}