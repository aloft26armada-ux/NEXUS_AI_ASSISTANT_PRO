package com.nexus.ai.core.performance

import android.app.ActivityManager
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

enum class PerformanceTier { LOW_RAM, BALANCED, FLAGSHIP_MAX }

data class EngineHardwareAllocation(
    val maxMemoryAllocationMb: Int,
    val optimalContextLength: Int,
    val threadsAllocation: Int,
    val useGpuAcceleration: Boolean
)

@Singleton
class DeviceMemoryProfileClassifier @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun evaluateHardwareProfileTier(): PerformanceTier {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        val totalGb = memoryInfo.totalMem / (1024 * 1024 * 1024).toDouble()
        
        return when {
            totalGb <= 6.5 -> PerformanceTier.LOW_RAM
            totalGb <= 9.5 -> PerformanceTier.BALANCED
            else -> PerformanceTier.FLAGSHIP_MAX
        }
    }

    fun computeCurrentHardwareAllocation(): EngineHardwareAllocation {
        return when (evaluateHardwareProfileTier()) {
            PerformanceTier.LOW_RAM -> EngineHardwareAllocation(
                maxMemoryAllocationMb = 1500,
                optimalContextLength = 1024,
                threadsAllocation = 4,
                useGpuAcceleration = false
            )
            PerformanceTier.BALANCED -> EngineHardwareAllocation(
                maxMemoryAllocationMb = 3000,
                optimalContextLength = 2048,
                threadsAllocation = 6,
                useGpuAcceleration = true
            )
            PerformanceTier.FLAGSHIP_MAX -> EngineHardwareAllocation(
                maxMemoryAllocationMb = 5500,
                optimalContextLength = 4096,
                threadsAllocation = 8,
                useGpuAcceleration = true
            )
        }
    }
}
