package com.towhid.postapp.domain.usecase

import com.towhid.postapp.domain.model.User
import com.towhid.postapp.domain.repository.UserRepository

class RegisterUserUseCase(private val repo: UserRepository) {
    suspend fun execute(user: User) = repo.register(user)

    suspend fun login(email: String, password: String) = repo.findByEmail(email, password)
}