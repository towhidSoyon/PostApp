package com.towhid.postapp.domain.usecase

import com.towhid.postapp.domain.model.Post
import com.towhid.postapp.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val repo: PostsRepository) {
    fun execute(): Flow<List<Post>> = repo.observePosts()
}