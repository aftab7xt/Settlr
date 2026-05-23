package com.settlr.app.ui.components.transaction

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.settlr.app.util.formatAmount

@Composable
fun AmountChips(
    onAmountSelected: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    val amounts = listOf(10.0, 20.0, 50.0, 100.0, 500.0)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        amounts.forEach { value ->
            SuggestionChip(
                onClick = { onAmountSelected(value) },
                label = { Text(formatAmount(value)) },
                modifier = Modifier.animateContentSize(
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                )
            )
        }
    }
}
