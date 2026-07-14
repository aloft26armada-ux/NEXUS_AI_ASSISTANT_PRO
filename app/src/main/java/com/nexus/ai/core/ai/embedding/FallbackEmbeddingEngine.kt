package com.nexus.ai.core.ai.embedding

import javax.inject.Inject

class FallbackEmbeddingEngine @Inject constructor() : EmbeddingEngine {
    override suspend fun generateEmbeddings(text: String): FloatArray {
        // Deterministic fallback for structural safety
        val fallback = FloatArray(384)
        for (i in fallback.indices) {
            fallback[i] = (text.hashCode() % (i + 1)) * 0.01f
        }
        return fallback
    }

    override suspend fun isAvailable(): Boolean {
        return true
    }
}
