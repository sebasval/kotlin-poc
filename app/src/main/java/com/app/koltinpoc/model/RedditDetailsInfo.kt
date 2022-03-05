package com.app.koltinpoc.model

import com.google.gson.annotations.SerializedName

data class RedditDetailsInfo(
    @SerializedName("title")
    val title: String,
    @SerializedName("subreddit")
    val subreddit: String,
    @SerializedName("thumbnail")
    val thumbnail: String
)
