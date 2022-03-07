package com.app.koltinpoc.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.koltinpoc.db.entity.ArticleEntity
import com.app.koltinpoc.db.entity.RedittInfoEntity

@Dao
interface RedditInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articleEntity: RedittInfoEntity):Long

    // NOTE - Since we are already using LIVE-DATA no need to use suspend function
    @Query("SELECT * FROM REDDIT")
    fun getAllOfflineArticles():LiveData<List<RedittInfoEntity>>

    @Delete
    suspend fun delete(articleEntity: RedittInfoEntity)
}