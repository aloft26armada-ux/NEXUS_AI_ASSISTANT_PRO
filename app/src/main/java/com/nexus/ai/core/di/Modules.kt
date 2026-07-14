package com.nexus.ai.core.di

import android.content.Context
import androidx.room.Room
import com.nexus.ai.core.ai.LlmInferenceEngine
import com.nexus.ai.core.ai.native.LlamaCppEngine
import com.nexus.ai.core.coroutines.DispatcherProvider
import com.nexus.ai.core.coroutines.NexusDispatchers
import com.nexus.ai.core.database.dao.*
import com.nexus.ai.data.db.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EngineBindingModule {
    @Binds
    @Singleton
    abstract fun bindInferenceEngine(implementation: LlamaCppEngine): LlmInferenceEngine

    @Binds
    @Singleton
    abstract fun bindDispatchers(dispatchers: NexusDispatchers): DispatcherProvider
}

@Module
@InstallIn(SingletonComponent::class)
object InfrastructureModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "nexus_secured_store.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideConversationDao(db: AppDatabase): ConversationDao = db.conversationDao()

    @Provides
    fun provideMessageDao(db: AppDatabase): MessageDao = db.messageDao()

    @Provides
    fun provideModelConfigDao(db: AppDatabase): ModelConfigDao = db.modelConfigDao()

    @Provides
    fun provideDocumentDao(db: AppDatabase): DocumentDao = db.documentDao()

    @Provides
    fun provideVectorEmbeddingDao(db: AppDatabase): VectorEmbeddingDao = db.vectorEmbeddingDao()

    @Provides
    fun provideUserSettingsDao(db: AppDatabase): UserSettingsDao = db.userSettingsDao()
}
