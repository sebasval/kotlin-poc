package com.app.koltinpoc.api

import com.app.koltinpoc.model.NewResponse
import com.app.koltinpoc.model.RedditInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TopHeadlinesApi {

    @GET("popular/top/.json")
    suspend fun getTopHeadlines(): Response<RedditInfo>
}