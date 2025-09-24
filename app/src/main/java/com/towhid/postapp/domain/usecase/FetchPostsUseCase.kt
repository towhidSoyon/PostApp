package com.towhid.postapp.domain.usecase

import com.towhid.postapp.domain.repository.PostsRepository
import io.github.aakira.napier.Napier

class FetchPostsUseCase(private val repo: PostsRepository) {
    suspend fun execute(page: Int, limit: Int) {
        repo.fetchPosts(page, limit)
    }
}