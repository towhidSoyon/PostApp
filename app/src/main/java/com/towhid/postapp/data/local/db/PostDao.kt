package com.towhid.postapp.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.towhid.postapp.data.local.model.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<PostEntity>)


    @Query("SELECT * FROM posts ORDER BY id ASC")
    fun observeAll(): Flow<List<PostEntity>>


    @Query("SELECT * FROM posts WHERE title LIKE '%' || :q || '%' OR body LIKE '%' || :q || '%' ORDER BY id ASC")
    fun search(q: String): Flow<List<PostEntity>>


    @Query("UPDATE posts SET isFavourite = :fav WHERE id = :id")
    suspend fun setFavourite(id: Int, fav: Boolean)


    @Query("SELECT * FROM posts WHERE isFavourite = 1 ORDER BY id ASC")
    fun observeFavourites(): Flow<List<PostEntity>>
}