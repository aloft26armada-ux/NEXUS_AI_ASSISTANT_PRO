package com.nexus.ai.core.ai.native

import com.nexus.ai.core.ai.InferenceToken
import com.nexus.ai.core.ai.LlmInferenceEngine
import com.nexus.ai.core.performance.DeviceMemoryProfileClassifier
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LlamaCppEngine @Inject constructor(
    private val hardwareClassifier: DeviceMemoryProfileClassifier
) : LlmInferenceEngine {

    private val contextHandle = AtomicLong(0)

    override suspend fun initializeEngine(modelPath: String): Boolean {
        if (contextHandle.get() != 0L) return true
        val allocation = hardwareClassifier.computeCurrentHardwareAllocation()
        val handle = LlamaNativeBridge.nativeInitializeEngine(
            modelPath,
            allocation.optimalContextLength,
            allocation.threadsAllocation
        )
        if (handle != 0L) {
            contextHandle.set(handle)
            return true
        }
        return false
    }

    override fun streamModelInference(prompt: String): Flow<InferenceToken> = callbackFlow {
        val currentHandle = contextHandle.get()
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
