package com.towhid.postapp.domain.repository

import com.towhid.postapp.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    fun observePosts(): Flow<List<Post>>
    suspend fun fetchPosts(page: Int, limit: Int)
    fun searchPosts(q: String): Flow<List<Post>>
    suspend fun setFavourite(postId: Int, fav: Boolean)
    fun observeFavourites(): Flow<List<Post>>

}