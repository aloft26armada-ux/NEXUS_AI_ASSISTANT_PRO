package com.nexus.ai.core.ai

import kotlinx.coroutines.flow.Flow

sealed interface InferenceToken {
    data class PartialChunk(val tokenText: String) : InferenceToken
    data object ProcessCompleted : InferenceToken
    data class OperationalFailure(val error: Throwable) : InferenceToken
}

interface LlmInferenceEngine {
    fun streamModelInference(prompt: String): Flow<InferenceToken>
    suspend fun initializeEngine(modelPath: String): Boolean
    suspend fun releaseActiveResources()
}
