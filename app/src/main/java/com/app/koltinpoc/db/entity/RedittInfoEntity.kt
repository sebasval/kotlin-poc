package com.app.koltinpoc.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "REDDIT")
data class RedittInfoEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val articleUrl: String?,
    val title: String?,
    val description: String?,
    val publishedState: Boolean?,
    val commentsCount: String
)