package com.towhid.postapp.di

import android.content.Context
import androidx.room.Room
import com.towhid.postapp.data.local.db.AppDatabase
import com.towhid.postapp.data.local.db.PostDao
import com.towhid.postapp.data.local.db.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): AppDatabase = Room.databaseBuilder(
        ctx, AppDatabase::class.java, "authposts_db"
    ).fallbackToDestructiveMigration().build()


    @Provides
    fun providePostDao(db: AppDatabase): PostDao = db.postDao()


    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()
}