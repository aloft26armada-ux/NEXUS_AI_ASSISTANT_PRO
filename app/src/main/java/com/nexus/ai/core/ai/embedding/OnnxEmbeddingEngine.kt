package com.nexus.ai.core.ai.embedding

import javax.inject.Inject

class OnnxEmbeddingEngine @Inject constructor() : EmbeddingEngine {
    override suspend fun generateEmbeddings(text: String): FloatArray {
        // Mock ONNX implementation due to missing native libraries/models in prompt context
        return FloatArray(384) { 0.1f }
    }

    override suspend fun isAvailable(): Boolean {
        return true
    }
}
