package com.nexus.ai.core.ai.native

import com.nexus.ai.core.ai.LlamaEngine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LlamaCppEngine @Inject constructor(
    private val nativeBridge: SafeNativeBridge
) : LlamaEngine {
    override suspend fun initialize(modelPath: String): Boolean {
        return nativeBridge.initializeLlama(modelPath)
    }

    override fun generateResponse(prompt: String): Flow<String> = flow {
        val result = nativeBridge.generateLlamaResponse(prompt)
        emit(result)
    }
}
        if (currentHandle == 0L) {
            trySend(InferenceToken.OperationalFailure(IllegalStateException("Engine structural pointer uninitialized.")))
            close()
            return@callbackFlow
        }

        LlamaNativeBridge.nativeExecuteInferenceStream(
            currentHandle,
            prompt,
            object : NativeInferenceListener {
                override fun onTokenGenerated(token: String) {
                    trySend(InferenceToken.PartialChunk(token))
                }

                override fun onInferenceComplete() {
                    trySend(InferenceToken.ProcessCompleted)
                    close()
                }
            }
        )
        awaitClose { /* Intercept native processing windows safely */ }
    }

    override suspend fun releaseActiveResources() {
        val currentHandle = contextHandle.getAndSet(0L)
        if (currentHandle != 0L) {
            LlamaNativeBridge.nativeShutdownEngine(currentHandle)
        }
    }
}
