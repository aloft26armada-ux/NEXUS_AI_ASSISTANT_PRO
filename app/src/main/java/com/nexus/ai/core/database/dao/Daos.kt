package com.nexus.ai.core.database.dao

import androidx.room.*
import com.nexus.ai.data.source.local.entity.*
import kotlinx.flow.Flow

@Dao
interface ConversationDao {
    @Query("SELECT * FROM conversations ORDER BY updated_at DESC")
    fun getAllConversations(): Flow<List<ConversationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversation(conversation: ConversationEntity)

    @Query("DELETE FROM conversations WHERE id = :id")
    suspend fun deleteConversationById(id: String)
}

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages WHERE conversation_id = :conversationId ORDER BY timestamp ASC")
    fun getMessagesForConversation(conversationId: String): Flow<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)
}

@Dao
interface ModelConfigDao {
    @Query("SELECT * FROM model_configs")
    fun getAllModels(): Flow<List<ModelConfigEntity>>

    @Query("SELECT * FROM model_configs WHERE id = :id")
    suspend fun getModelById(id: String): ModelConfigEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModel(model: ModelConfigEntity)

    @Query("UPDATE model_configs SET is_downloaded = :downloaded, local_file_path = :path WHERE id = :id")
    suspend fun updateDownloadStatus(id: String, downloaded: Boolean, path: String?)
}

@Dao
interface DocumentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocument(document: DocumentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChunks(chunks: List<DocumentChunkEntity>)

    @Query("SELECT * FROM documents")
    fun getAllDocuments(): Flow<List<DocumentEntity>>
}

@Dao
interface VectorEmbeddingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmbedding(embedding: VectorEmbeddingEntity)

    @Query("SELECT ve.*, dc.content FROM vector_embeddings ve JOIN document_chunks dc ON ve.chunk_id = dc.id")
    suspend fun getAllEmbeddingsWithContent(): List<EmbeddingWithContentTuple>
}

data class EmbeddingWithContentTuple(
    @Embedded val embedding: VectorEmbeddingEntity,
    @ColumnInfo(name = "content") val content: String
)

@Dao
interface UserSettingsDao {
    @Query("SELECT * FROM user_settings WHERE id = 'system_default'")
    fun getSettings(): Flow<UserSettingsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: UserSettingsEntity)
}

