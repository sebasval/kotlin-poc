package com.app.koltinpoc.db.dao

import androidx.room.*
import com.app.koltinpoc.db.entity.AnimeInfoEntity

@Dao
interface RedditInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articleEntity: AnimeInfoEntity): Long

    // NOTE - Since we are already using LIVE-DATA no need to use suspend function
    @Query("SELECT * FROM REDDIT")
    fun getAllRedditInfo(): List<AnimeInfoEntity>

    @Query("DELETE FROM REDDIT WHERE description = :subreddit")
    suspend fun delete(subreddit: String)

    @Query("DELETE FROM REDDIT")
    suspend fun deleteAll()

    @Query("UPDATE REDDIT SET publishedState = :state WHERE id = :id")
    suspend fun updateReadStatusElement(id: Int, state: Boolean)
}