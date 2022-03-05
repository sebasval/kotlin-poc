package com.app.koltinpoc.di

import com.app.koltinpoc.api.TopHeadlinesApi
import com.app.koltinpoc.model.NewResponse
import com.app.koltinpoc.model.RedditInfo
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    val topHeadlinesApi: TopHeadlinesApi
) {

    suspend fun getTopHeadlines(): Response<RedditInfo> {
        return topHeadlinesApi.getTopHeadlines()
    }

}