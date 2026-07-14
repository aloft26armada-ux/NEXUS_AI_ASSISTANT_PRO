package com.nexus.ai.core.ai.assets

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StartupValidation @Inject constructor(
    private val assetVerifier: AssetVerifier
) {
    fun isLlamaModelAvailable(): Boolean {
        return assetVerifier.verifyAssetPresent("llama_model.bin")
    }

    fun isOnnxModelAvailable(): Boolean {
        return assetVerifier.verifyAssetPresent("embedding_model.onnx")
    }
}
