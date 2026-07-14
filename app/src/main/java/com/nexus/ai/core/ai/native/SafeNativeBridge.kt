package com.nexus.ai.core.ai.native

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SafeNativeBridge @Inject constructor(
    private val capabilityDetector: NativeCapabilityDetector,
    private val fallbackEngine: KotlinFallbackEngine
) {
    private external fun initNativeLlama(modelPath: String): Boolean
    private external fun generateNativeResponse(prompt: String): String
    private external fun initNativeWhisper(modelPath: String): Boolean

    fun initializeLlama(modelPath: String): Boolean {
        return if (capabilityDetector.isNativeAvailable()) {
            try {
                initNativeLlama(modelPath)
            } catch (e: UnsatisfiedLinkError) {
                false
            }
        } else {
            true // Fallback init
        }
    }

    fun generateLlamaResponse(prompt: String): String {
        return if (capabilityDetector.isNativeAvailable()) {
            try {
                generateNativeResponse(prompt)
            } catch (e: UnsatisfiedLinkError) {
                fallbackEngine.generateResponse(prompt)
            }
        } else {
            fallbackEngine.generateResponse(prompt)
        }
    }
}
