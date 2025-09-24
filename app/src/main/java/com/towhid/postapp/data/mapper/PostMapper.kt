package com.towhid.postapp.data.mapper

import com.towhid.postapp.data.local.model.PostEntity
import com.towhid.postapp.domain.model.Post

object PostMapper {
    fun entityToDomain(e: PostEntity): Post = Post(e.id, e.userId, e.title, e.body, e.isFavourite)
    fun domainToEntity(d: Post): PostEntity = PostEntity(d.id, d.userId, d.title, d.body, d.isFavourite)
}