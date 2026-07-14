package com.nexus.ai.core.ai.native

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NativeCapabilityDetector @Inject constructor() {
    private var isNativeAvailable = false

    init {
        try {
            System.loadLibrary("nexus_llama_bridge")
            System.loadLibrary("nexus_whisper_bridge")
            isNativeAvailable = true
        } catch (e: UnsatisfiedLinkError) {
            Log.e("NativeDetector", "Failed to load native libraries", e)
            isNativeAvailable = false
        }
    }

    fun isNativeAvailable(): Boolean = isNativeAvailable
}
