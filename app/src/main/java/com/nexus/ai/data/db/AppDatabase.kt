package com.nexus.ai.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nexus.ai.core.database.dao.ChatDao
import com.nexus.ai.data.source.local.entity.ChatEntity

@Database(entities = [ChatEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
}
