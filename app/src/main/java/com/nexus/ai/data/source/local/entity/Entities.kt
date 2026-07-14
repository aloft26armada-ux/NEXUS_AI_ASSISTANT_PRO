package com.nexus.ai.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "conversations")
data class ConversationEntity(
    @PrimaryKey val id: String,
    val title: String,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: Long
)

@Entity(
    tableName = "messages",
    foreignKeys = [
        ForeignKey(
            entity = ConversationEntity::class,
            parentColumns = ["id"],
            childColumns = ["conversation_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["conversation_id"]), Index(value = ["timestamp"])]
)
data class MessageEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "conversation_id") val conversationId: String,
    val role: String,
    val content: String,
    val timestamp: Long,
    @ColumnInfo(name = "has_citations") val hasCitations: Boolean
)

@Entity(tableName = "model_configs")
data class ModelConfigEntity(
    @PrimaryKey val id: String,
    val name: String,
    @ColumnInfo(name = "local_file_path") val localFilePath: String?,
    @ColumnInfo(name = "download_url") val downloadUrl: String,
    @ColumnInfo(name = "expected_sha256") val expectedSha256: String,
    @ColumnInfo(name = "is_downloaded") val isDownloaded: Boolean,
    @ColumnInfo(name = "file_size_bytes") val fileSizeBytes: Long
)

@Entity(tableName = "documents")
data class DocumentEntity(
    @PrimaryKey val id: String,
    val name: String,
    @ColumnInfo(name = "file_path") val filePath: String,
    @ColumnInfo(name = "mime_type") val mimeType: String,
    @ColumnInfo(name = "indexed_at") val indexedAt: Long
)

@Entity(
    tableName = "document_chunks",
    foreignKeys = [
        ForeignKey(
            entity = DocumentEntity::class,
            parentColumns = ["id"],
            childColumns = ["document_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["document_id"])]
)
data class DocumentChunkEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "document_id") val documentId: String,
    @ColumnInfo(name = "page_index") val pageIndex: Int,
    val content: String
)

@Entity(
    tableName = "vector_embeddings",
    foreignKeys = [
        ForeignKey(
            entity = DocumentChunkEntity::class,
            parentColumns = ["id"],
            childColumns = ["chunk_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["chunk_id"])]
)
data class VectorEmbeddingEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "chunk_id") val chunkId: String,
    @ColumnInfo(name = "vector_data") val vectorData: String // Serialized dynamic space coordinates array
)

@Entity(tableName = "user_settings")
data class UserSettingsEntity(
    @PrimaryKey val id: String = "system_default",
    @ColumnInfo(name = "theme_mode") val themeMode: String,
    @ColumnInfo(name = "performance_profile") val performanceProfile: String,
    @ColumnInfo(name = "haptics_enabled") val hapticsEnabled: Boolean,
    @ColumnInfo(name = "active_model_id") val activeModelId: String?
)
