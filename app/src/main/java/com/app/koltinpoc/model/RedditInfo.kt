package com.app.koltinpoc.model

import com.google.gson.annotations.SerializedName

data class RedditInfo(
    @SerializedName("kind")
    val kind: String,
    @SerializedName("data")
    val data: RedditData
)
