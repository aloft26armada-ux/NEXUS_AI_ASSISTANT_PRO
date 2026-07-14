package com.nexus.ai.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey val id: String,
    val message: String,
    val isFromUser: Boolean,
    val timestamp: Long
)
