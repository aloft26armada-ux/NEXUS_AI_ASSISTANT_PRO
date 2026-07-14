package com.nexus.ai.core.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromFloatList(value: List<Float>?): String? {
        return value?.joinToString(",") { it.toString() }
    }

    @TypeConverter
    fun toFloatList(value: String?): List<Float>? {
        return value?.split(",")?.mapNotNull { it.toFloatOrNull() }
    }
}
