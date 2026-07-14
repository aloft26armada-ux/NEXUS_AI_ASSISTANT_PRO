package com.nexus.ai.core.ai.native

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KotlinFallbackEngine @Inject constructor() {
    fun generateResponse(prompt: String): String {
        return "Kotlin Fallback Engine: Cannot load native JNI libraries. Processing prompt '$prompt' in safe mode."
    }
}
