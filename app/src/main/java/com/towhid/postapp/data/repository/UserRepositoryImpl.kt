package com.towhid.postapp.data.repository

import com.towhid.postapp.data.local.db.UserDao
import com.towhid.postapp.data.local.model.UserEntity
import com.towhid.postapp.domain.model.User
import com.towhid.postapp.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(private val userDao: UserDao) : UserRepository {
    override suspend fun register(user: User) {
        userDao.insert(UserEntity(email = user.email, passwordHash = user.passwordHash))
    }


    override suspend fun findByEmail(email: String): Boolean {
        val e = userDao.findByEmail(email)
        return e!=null

    }
}