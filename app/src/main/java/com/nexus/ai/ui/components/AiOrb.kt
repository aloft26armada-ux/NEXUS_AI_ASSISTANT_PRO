package com.nexus.ai.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.dp
import kotlin.math.sin

@Composable
fun AiOrb(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "OrbPulseLoop")
    val pulseScale by transition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "SizePulse"
    )

    Canvas(modifier = modifier.size(220.dp)) {
        val centerCoordinates = size / 2.0f
        val calculatedRadius = (size.minDimension / 2.0f) * pulseScale

        scale(pulseScale, centerCoordinates.x, centerCoordinates.y) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF00F5D4),
                        Color(0xFF7B2CBF).copy(alpha = 0.6f),
                        Color(0xFF240046).copy(alpha = 0.0f)
                    ),
                    center = centerCoordinates,
                    radius = calculatedRadius
                ),
                radius = calculatedRadius,
                center = centerCoordinates
            )
        }
    }
}
