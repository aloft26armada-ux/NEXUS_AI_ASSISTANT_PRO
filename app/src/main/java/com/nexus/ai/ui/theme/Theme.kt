package com.nexus.ai.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF00F5D4),
    secondary = Color(0xFF7B2CBF),
    tertiary = Color(0xFF9D4EDD),
    background = Color(0xFF0B001A),
    surface = Color(0xFF160033),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFFFFFFFF),
    onBackground = Color(0xFFE0AAFF),
    onSurface = Color(0xFFE0AAFF)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF7B2CBF),
    secondary = Color(0xFF00F5D4),
    tertiary = Color(0xFF5A189A),
    background = Color(0xFFF3EDF7),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFF004B40),
    onBackground = Color(0xFF240046),
    onSurface = Color(0xFF240046)
)

@Composable
fun NexusAIAssistantTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set false to override dynamic theme matrices with NEXUS branding profiles
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
