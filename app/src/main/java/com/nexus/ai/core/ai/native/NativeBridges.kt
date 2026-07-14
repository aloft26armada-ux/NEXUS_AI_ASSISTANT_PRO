package com.nexus.ai.core.ai.native

import android.util.Log

interface NativeInferenceListener {
    fun onTokenGenerated(token: String)
    fun onInferenceComplete()
}

object LlamaNativeBridge {
    init {
        try {
            System.loadLibrary("nexus_ai")
        } catch (e: UnsatisfiedLinkError) {
            Log.e("LlamaNativeBridge", "Native implementation binary map unavailable.", e)
        }
    }

    external fun nativeInitializeEngine(modelPath: String, tokensCount: Int, threadAllocationCount: Int): Long
    external fun nativeExecuteInferenceStream(contextHandle: Long, promptInput: String, callbackListener: NativeInferenceListener)
    external fun nativeShutdownEngine(contextHandle: Long)
}

object WhisperNativeBridge {
    init {
        try {
            System.loadLibrary("nexus_ai")
        } catch (e: UnsatisfiedLinkError) {
            Log.e("WhisperNativeBridge", "Native audio library map unavailable.", e)
        }
    }

    external fun nativeInitAudioContext(assetPath: String): Long
    external fun nativeTranscribeAudioBuffer(contextHandle: Long, pcmData: FloatArray, length: Int): String
    external fun nativeReleaseAudioContext(contextHandle: Long)
}
