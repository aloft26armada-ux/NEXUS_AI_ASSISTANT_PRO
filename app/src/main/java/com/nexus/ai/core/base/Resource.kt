package com.nexus.ai.core.base

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Throwable, val message: String? = null) : Resource<Nothing>()
    data class Loading(val progress: Float = 0f) : Resource<Nothing>()
    data object Idle : Resource<Nothing>()
}
