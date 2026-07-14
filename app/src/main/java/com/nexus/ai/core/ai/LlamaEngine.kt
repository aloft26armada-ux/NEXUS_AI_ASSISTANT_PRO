package com.nexus.ai.core.ai

import kotlinx.coroutines.flow.Flow

interface LlamaEngine {
    suspend fun initialize(modelPath: String): Boolean
    fun generateResponse(prompt: String): Flow<String>
}
