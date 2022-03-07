package com.app.koltinpoc.di

import com.app.koltinpoc.db.AppDatabase
import com.app.koltinpoc.di.Transformer.convertEntityRedditListToRedditInfoList
import com.app.koltinpoc.di.Transformer.convertRedditInfoToRedditInfoEntity
import com.app.koltinpoc.model.RedditInfo
import com.app.koltinpoc.model.RedditListInfo
import com.app.koltinpoc.utils.DataHandler
import java.io.IOException
import javax.inject.Inject

class DBRepository @Inject constructor(val appDatabase: AppDatabase) {

    suspend fun deleteElementByName(redditListInfo: RedditListInfo): DataHandler<Unit> {
        return try {
            appDatabase.redditInfo().delete(redditListInfo.data.subreddit)
            DataHandler.SUCCESS(Unit)
        } catch (e: IOException) {
            DataHandler.ERROR(message = "Could not delete element")
        }
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