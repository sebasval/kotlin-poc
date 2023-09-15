package com.app.koltinpoc.di

import com.app.koltinpoc.db.AppDatabase
import com.app.koltinpoc.di.Transformer.convertAnimeDataToRedditInfoEntity
import com.app.koltinpoc.di.Transformer.convertEntityRedditListToAnimeDataList
import com.app.koltinpoc.model.AnimeData
import com.app.koltinpoc.model.AnimeInfo
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

    suspend fun deleteAllElements(): DataHandler<Unit> {
        return try {
            appDatabase.redditInfo().deleteAll()
            DataHandler.SUCCESS(Unit)
        } catch (e: IOException) {
            DataHandler.ERROR(message = "could not delete all elements")
        }
    }


    suspend fun insertAnimeInfo(animeInfo: AnimeInfo): DataHandler<Unit> {
        return try {
            animeInfo.data.forEach {
                appDatabase.redditInfo()
                    .insert(convertAnimeDataToRedditInfoEntity(it))
            }
            DataHandler.SUCCESS(Unit)
        } catch (e: IOException) {
            DataHandler.ERROR(message = "Could not save items")
        }
    }

    suspend fun getAllRedditInfo(): List<AnimeData> {
        return convertEntityRedditListToAnimeDataList(appDatabase.redditInfo().getAllRedditInfo())
    }

    suspend fun updateElementState(redditListInfo: RedditListInfo): DataHandler<Unit> {
        return try {
            appDatabase.redditInfo().updateReadStatusElement(redditListInfo.data.idElement.toInt(), true)
            DataHandler.SUCCESS(Unit)
        } catch (e: IOException) {
            DataHandler.ERROR(message = e.message.toString())
        }
    }
}