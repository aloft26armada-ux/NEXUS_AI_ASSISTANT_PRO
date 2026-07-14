package com.nexus.ai.core.ai.assets

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelLocator @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getLlamaModelPath(): String {
        return File(context.filesDir, "llama_model.bin").absolutePath
    }

    fun getOnnxModelPath(): String {
        return File(context.filesDir, "embedding_model.onnx").absolutePath
    }
}
