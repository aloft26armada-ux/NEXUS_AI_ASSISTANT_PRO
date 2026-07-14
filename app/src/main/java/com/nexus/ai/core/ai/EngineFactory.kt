package com.nexus.ai.core.ai

import com.nexus.ai.core.ai.native.LlamaCppEngine
import com.nexus.ai.core.ai.assets.StartupValidation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EngineFactory @Inject constructor(
    private val realEngine: LlamaCppEngine,
    private val fallbackEngine: FallbackLlamaEngine,
    private val startupValidation: StartupValidation
) {
    fun getLlamaEngine(): LlamaEngine {
        return if (startupValidation.isLlamaModelAvailable()) {
            realEngine
        } else {
            fallbackEngine
        }
    }
}
