package com.app.koltinpoc.di

import androidx.lifecycle.LiveData
import com.app.koltinpoc.db.entity.ArticleEntity
import com.app.koltinpoc.db.entity.RedittInfoEntity
import com.app.koltinpoc.db.entity.SourceEntity
import com.app.koltinpoc.model.Article
import com.app.koltinpoc.model.RedditDetailsInfo
import com.app.koltinpoc.model.RedditListInfo
import com.app.koltinpoc.model.Source

/*
* This is a transformer class
* We cannot just use our model classes for inserting or getting data from db
* There might be a time when variables might be added or removed in model classes
* So it is always a better practice to have transformer classes
* */
object Transformer {

    fun convertRedditInfoToRedditInfoEntity(redditInfoList: RedditListInfo): RedittInfoEntity {
        return redditInfoList.let {
            RedittInfoEntity(
                articleUrl = it.data.thumbnail,
                title = it.data.title,
                description = it.data.subreddit,
                publishedState = false,
                commentsCount = it.data.commentsCount.toString()
            )
        }
    }

    fun convertEntityRedditListToRedditInfoList(redditInfoList: List<RedittInfoEntity>): List<RedditListInfo> {
        val redditList = redditInfoList.map {
            RedditListInfo(
                kind = "",
                data = RedditDetailsInfo(
                    title = it.title!!,
                    subreddit = it.description!!,
                    thumbnail = it.articleUrl!!,
                    commentsCount = it.commentsCount.toLong(),
                    readStatus = it.publishedState!!,
                    idElement = it.id.toString()
                )
            )
        }
        return redditList
    }

    fun convertArticleModelToArticleEntity(article: Article): ArticleEntity {
        return ArticleEntity(
            author = article.author,
            content = article.content,
            source = convertSourceModelToSourceEntity(article.source),
            description = article.description,
            publishedAt = article.publishedAt,
            url = article.url,
            urlToImage = article.urlToImage,
            title = article.title

        )
    }

    fun convertArticleEntityToArticleModel(articleEntity: ArticleEntity): Article {
        return Article(
            author = articleEntity.author,
            content = articleEntity.content,
            source = convertSourceEntityToSourceModel(articleEntity.source),
            description = articleEntity.description,
            publishedAt = articleEntity.publishedAt,
            url = articleEntity.url,
            urlToImage = articleEntity.urlToImage,
            title = articleEntity.title

        )
    }

    private fun convertSourceModelToSourceEntity(source: Source?): SourceEntity? {

        source?.let {
            return SourceEntity(source.id, source.name)
        }

        return null
    }

    private fun convertSourceEntityToSourceModel(sourceEntity: SourceEntity?): Source? {

        sourceEntity?.let {

            return Source(sourceEntity.id, sourceEntity.name)
        }

        return null
    }
}