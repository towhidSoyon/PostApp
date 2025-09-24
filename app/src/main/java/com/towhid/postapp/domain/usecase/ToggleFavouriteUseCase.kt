package com.towhid.postapp.domain.usecase

import com.towhid.postapp.domain.repository.PostsRepository

class ToggleFavouriteUseCase(private val repo: PostsRepository) {
    suspend fun execute(postId: Int, fav: Boolean) = repo.setFavourite(postId, fav)
}