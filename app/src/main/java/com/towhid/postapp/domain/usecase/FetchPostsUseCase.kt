package com.towhid.postapp.domain.usecase

import com.towhid.postapp.domain.repository.PostsRepository
import javax.inject.Inject

class FetchPostsUseCase @Inject constructor(private val repo: PostsRepository) {
    suspend fun execute(page: Int, limit: Int) {
        repo.fetchPosts(page, limit)
    }
}