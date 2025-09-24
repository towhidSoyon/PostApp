package com.towhid.postapp.data.remote

import com.towhid.postapp.data.local.model.PostEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("posts")
    suspend fun getPosts(@Query("_page") page: Int = 1, @Query("_limit") limit: Int = 20): List<PostDto>
}