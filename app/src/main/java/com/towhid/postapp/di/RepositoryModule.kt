package com.towhid.postapp.di

import com.towhid.postapp.data.repository.PostsRepositoryImpl
import com.towhid.postapp.data.repository.UserRepositoryImpl
import com.towhid.postapp.domain.repository.PostsRepository
import com.towhid.postapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPostsRepository(impl: PostsRepositoryImpl): PostsRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}