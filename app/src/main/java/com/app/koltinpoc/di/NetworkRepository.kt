package com.app.koltinpoc.di

import com.app.koltinpoc.api.TopHeadlinesApi
import com.app.koltinpoc.model.AnimeInfo
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    val topHeadlinesApi: TopHeadlinesApi
) {

    suspend fun getTopHeadlines(): Response<AnimeInfo> {
        return topHeadlinesApi.getTopHeadlines()
    }

}