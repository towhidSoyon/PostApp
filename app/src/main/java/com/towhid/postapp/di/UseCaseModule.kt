package com.towhid.postapp.di

import com.towhid.postapp.domain.repository.PostsRepository
import com.towhid.postapp.domain.repository.UserRepository
import com.towhid.postapp.domain.usecase.FetchPostsUseCase
import com.towhid.postapp.domain.usecase.GetPostsUseCase
import com.towhid.postapp.domain.usecase.RegisterUserUseCase
import com.towhid.postapp.domain.usecase.SearchPostsUseCase
import com.towhid.postapp.domain.usecase.ToggleFavouriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {


    @Provides
    fun provideGetPostsUseCase(repo: PostsRepository) = GetPostsUseCase(repo)


    @Provides
    fun provideFetchPostsUseCase(repo: PostsRepository) = FetchPostsUseCase(repo)


    @Provides
    fun provideSearchPostsUseCase(repo: PostsRepository) = SearchPostsUseCase(repo)


    @Provides
    fun provideToggleFavUseCase(repo: PostsRepository) = ToggleFavouriteUseCase(repo)


    @Provides
    fun provideRegisterUserUseCase(userRepo: UserRepository) = RegisterUserUseCase(userRepo)
}