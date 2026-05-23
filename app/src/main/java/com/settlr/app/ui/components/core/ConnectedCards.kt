package com.settlr.app.ui.components.core

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ConnectedCardGroup(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        content = content
    )
}

@Composable
fun ConnectedCard(
    index: Int,
    totalItems: Int,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    color: Color = colorScheme.surfaceContainerLow,
    largeRadius: Dp = 20.dp,
    smallRadius: Dp = 4.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val isFirst = index == 0
    val isLast = index == totalItems - 1
    val isOnly = totalItems == 1

    val topRadius by animateDpAsState(
        targetValue = if (isPressed || isFirst || isOnly) largeRadius else smallRadius,
        label = "cardTopRadius"
    )
    val bottomRadius by animateDpAsState(
        targetValue = if (isPressed || isLast || isOnly) largeRadius else smallRadius,
        label = "cardBottomRadius"
    )

    val shape = RoundedCornerShape(
        topStart = topRadius,
        topEnd = topRadius,
        bottomEnd = bottomRadius,
        bottomStart = bottomRadius
    )

    Surface(
        shape = shape,
        color = color,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .then(
                if (onClick != null) {
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onClick
                    )
                } else Modifier
            )
    ) {
        Column(content = content)
    }
}
