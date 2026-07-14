package com.nexus.ai.core.ai.embedding

interface EmbeddingEngine {
    suspend fun generateEmbeddings(text: String): FloatArray
    suspend fun isAvailable(): Boolean
}
