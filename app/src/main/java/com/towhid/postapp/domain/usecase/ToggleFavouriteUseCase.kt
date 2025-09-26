package com.towhid.postapp.domain.usecase

import com.towhid.postapp.domain.repository.PostsRepository
import javax.inject.Inject

class ToggleFavouriteUseCase @Inject constructor(private val repo: PostsRepository) {
    suspend fun execute(postId: Int, fav: Boolean) = repo.setFavourite(postId, fav)
}