package com.towhid.postapp.data.repository

import com.towhid.postapp.data.local.db.PostDao
import com.towhid.postapp.data.local.model.PostEntity
import com.towhid.postapp.data.mapper.PostMapper
import com.towhid.postapp.data.remote.ApiService
import com.towhid.postapp.domain.model.Post
import com.towhid.postapp.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val postDao: PostDao
) : PostsRepository {


    override fun observePosts(): Flow<List<Post>> =
        postDao.observeAll().map { list -> list.map { PostMapper.entityToDomain(it) } }


    override suspend fun fetchPosts(page: Int, limit: Int) {
        try {
            val remote = api.getPosts(page, limit)
            val entities = remote.map { dto ->
                PostEntity(
                    id = dto.id,
                    userId = dto.userId,
                    title = dto.title,
                    body = dto.body,
                    isFavourite = false
                )
            }
            postDao.insertAll(entities)
        } catch (e: Exception) {
        }
    }


    override fun searchPosts(q: String): Flow<List<Post>> =
        postDao.search(q).map { list -> list.map { PostMapper.entityToDomain(it) } }


    override suspend fun setFavourite(postId: Int, fav: Boolean) = postDao.setFavourite(postId, fav)


    override fun observeFavourites(): Flow<List<Post>> =
        postDao.observeFavourites().map { list -> list.map { PostMapper.entityToDomain(it) } }

}