package com.nexus.ai.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nexus.ai.core.database.Converters
import com.nexus.ai.core.database.dao.*
import com.nexus.ai.data.source.local.entity.*

@Database(
    entities = [
        ConversationEntity::class,
        MessageEntity::class,
        ModelConfigEntity::class,
        DocumentEntity::class,
        DocumentChunkEntity::class,
        VectorEmbeddingEntity::class,
        UserSettingsEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun conversationDao(): ConversationDao
    abstract fun messageDao(): MessageDao
    abstract fun modelConfigDao(): ModelConfigDao
    abstract fun documentDao(): DocumentDao
    abstract fun vectorEmbeddingDao(): VectorEmbeddingDao
    abstract fun userSettingsDao(): UserSettingsDao
}
