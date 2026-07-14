package com.nexus.ai.core.ai.embedding

import kotlin.math.sqrt

object VectorMathUtils {
    
    fun calculateCosineSimilarity(vectorA: FloatArray, vectorB: FloatArray): Float {
        if (vectorA.size != vectorB.size) return 0f
        var dotProduct = 0.0f
        var normA = 0.0f
        var normB = 0.0f
        
        for (i in vectorA.indices) {
            dotProduct += vectorA[i] * vectorB[i]
            normA += vectorA[i] * vectorA[i]
            normB += vectorB[i] * vectorB[i]
        }
        
        if (normA == 0.0f || normB == 0.0f) return 0f
        return (dotProduct / (sqrt(normA.toDouble()) * sqrt(normB.toDouble()))).toFloat()
    }

    fun parseEmbeddingString(data: String): FloatArray {
        return data.split(",").map { it.toFloat() }.toFloatArray()
    }
}
