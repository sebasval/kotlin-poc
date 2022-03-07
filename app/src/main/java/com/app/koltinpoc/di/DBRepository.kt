package com.app.koltinpoc.di

import androidx.lifecycle.LiveData
import com.app.koltinpoc.db.AppDatabase
import com.app.koltinpoc.db.entity.ArticleEntity
import com.app.koltinpoc.db.entity.RedittInfoEntity
import com.app.koltinpoc.di.Transformer.convertArticleModelToArticleEntity
import com.app.koltinpoc.di.Transformer.convertEntityRedditListToRedditInfoList
import com.app.koltinpoc.di.Transformer.convertRedditInfoToRedditInfoEntity
import com.app.koltinpoc.model.Article
import com.app.koltinpoc.model.RedditInfo
import com.app.koltinpoc.model.RedditListInfo
import com.app.koltinpoc.utils.DataHandler
import java.io.IOException
import javax.inject.Inject

class DBRepository @Inject constructor(val appDatabase: AppDatabase) {

    suspend fun insertArticle(article: Article): Long {
        return appDatabase.articleDao()
            .insert(convertArticleModelToArticleEntity(article))
    }

    suspend fun delete(article: Article) {
        appDatabase.articleDao().delete(convertArticleModelToArticleEntity(article))
    }

    // NOTE - Since we are already using LIVE-DATA no need to use suspend function
    fun getAllArticles(): LiveData<List<ArticleEntity>> {
        return appDatabase.articleDao().getAllOfflineArticles()
    }

    suspend fun insertRedditInfo(redditInfo: RedditInfo): DataHandler<Unit> {
        return try {
            redditInfo.data.children.forEach {
                appDatabase.redditInfo()
                    .insert(convertRedditInfoToRedditInfoEntity(it))
            }
            DataHandler.SUCCESS(Unit)
        } catch (e: IOException) {
            DataHandler.ERROR(message = "Could not save items")
        }
    }

    suspend fun getAllRedditInfo(): List<RedditListInfo> {
        return convertEntityRedditListToRedditInfoList(appDatabase.redditInfo().getAllRedditInfo())
    }
}