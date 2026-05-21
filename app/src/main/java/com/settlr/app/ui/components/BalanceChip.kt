package com.settlr.app.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.settlr.app.ui.theme.BalanceNegative
import com.settlr.app.ui.theme.BalancePositive
import com.settlr.app.util.formatAmount

@Composable
fun BalanceChip(
    amount: Double,
    modifier: Modifier = Modifier
) {
    val isPositive = amount > 0
    val isNegative = amount < 0

    val containerColor = when {
        isPositive -> BalancePositive.copy(alpha = 0.15f)
        isNegative -> BalanceNegative.copy(alpha = 0.15f)
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    val textColor = when {
        isPositive -> BalancePositive
        isNegative -> BalanceNegative
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    val prefix = when {
        isPositive -> "+"
        isNegative -> "-"
        else -> ""
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = containerColor
    ) {
        Text(
            text = "$prefix${formatAmount(amount)}",
            color = textColor,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
