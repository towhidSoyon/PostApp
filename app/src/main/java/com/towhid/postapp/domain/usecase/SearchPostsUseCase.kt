package com.towhid.postapp.domain.usecase

import com.towhid.postapp.domain.model.Post
import com.towhid.postapp.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow

class SearchPostsUseCase(private val repo: PostsRepository) {
    fun execute(q: String): Flow<List<Post>> = repo.searchPosts(q)
}