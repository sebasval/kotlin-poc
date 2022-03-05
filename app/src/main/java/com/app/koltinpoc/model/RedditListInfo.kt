package com.app.koltinpoc.model

import com.google.gson.annotations.SerializedName

data class  RedditListInfo(
    @SerializedName("kind")
    val kind: String,
    @SerializedName("data")
    val data: RedditDetailsInfo
)
