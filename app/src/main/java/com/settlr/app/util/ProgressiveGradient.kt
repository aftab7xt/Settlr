package com.settlr.app.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.progressiveGradient(
    startY: Float,
    endY: Float,
    tintColor: Color = Color.Black,
    tintAlpha: Float = 0.95f
): Modifier = this.drawWithContent {
    drawContent()
    drawRect(
        brush = Brush.verticalGradient(
            colorStops = arrayOf(
                0.0f to tintColor.copy(alpha = tintAlpha),
                1.0f to Color.Transparent
            ),
            startY = startY,
            endY   = endY
        )
    )
}