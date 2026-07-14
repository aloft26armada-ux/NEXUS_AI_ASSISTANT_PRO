package com.nexus.ai.core.ai.assets

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AssetVerifier @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun verifyAssetPresent(fileName: String): Boolean {
        val file = File(context.filesDir, fileName)
        return file.exists() && file.length() > 0
    }
}
