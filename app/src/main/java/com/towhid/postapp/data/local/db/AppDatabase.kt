package com.towhid.postapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.towhid.postapp.data.local.model.PostEntity
import com.towhid.postapp.data.local.model.UserEntity

@Database(entities = [PostEntity::class, UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
}