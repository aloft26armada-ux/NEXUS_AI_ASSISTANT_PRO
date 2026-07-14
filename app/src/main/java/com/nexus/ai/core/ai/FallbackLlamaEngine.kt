package com.nexus.ai.core.ai

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FallbackLlamaEngine @Inject constructor() : LlamaEngine {
    override suspend fun initialize(modelPath: String): Boolean {
        return true
    }

    override fun generateResponse(prompt: String): Flow<String> = flow {
        val words = "Fallback response: The native AI model is missing or could not be loaded. Please ensure models are downloaded.".split(" ")
        for (word in words) {
            emit("$word ")
            delay(50)
        }
    }
}
