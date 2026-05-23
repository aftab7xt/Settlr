package com.settlr.app.ui.components.core

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BackIcon(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Rounded.ChevronLeft,
        contentDescription = "Back",
        modifier = modifier.size(28.dp)
    )
}
