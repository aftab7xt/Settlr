package com.settlr.app.ui.components.feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.settlr.app.ui.theme.BalanceNegative
import com.settlr.app.ui.theme.BalancePositive
import com.settlr.app.util.formatAmount

@Composable
fun SummaryHeader(
    totalOwedToMe: Double,
    totalYouOwe: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SummaryItem(
            label = "owed to you",
            amount = totalOwedToMe,
            positive = true
        )
        SummaryItem(
            label = "you owe",
            amount = totalYouOwe,
            positive = false,
            alignment = Alignment.End
        )
    }
}

@Composable
private fun SummaryItem(
    label: String,
    amount: Double,
    positive: Boolean,
    alignment: Alignment.Horizontal = Alignment.Start
) {
    Column(horizontalAlignment = alignment) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = formatAmount(amount),
            style = MaterialTheme.typography.titleLarge,
            color = if (positive) BalancePositive else BalanceNegative
        )
    }
}