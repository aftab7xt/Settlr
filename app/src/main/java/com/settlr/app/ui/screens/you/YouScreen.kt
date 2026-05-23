package com.settlr.app.ui.screens.you

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.settlr.app.ui.components.core.ConnectedCard
import com.settlr.app.ui.components.core.ConnectedCardGroup
import com.settlr.app.ui.theme.BalanceNegative
import com.settlr.app.ui.theme.BalancePositive
import com.settlr.app.ui.theme.RobotoFlex
import com.settlr.app.util.formatAmount
import org.koin.androidx.compose.koinViewModel

@Composable
fun YouScreen(
    modifier: Modifier = Modifier,
    viewModel: YouViewModel = koinViewModel()
) {
    val netBalance by viewModel.netBalance.collectAsState()
    val people by viewModel.people.collectAsState()
    val entries by viewModel.entries.collectAsState()

    val activeBalancesCount = remember(people, entries) {
        people.count { person ->
            val personEntries = entries.filter { it.personId == person.id && !it.isSettled }
            var bal = 0.0
            personEntries.forEach { 
                if (it.isOwedToMe) bal += it.amount else bal -= it.amount 
            }
            bal != 0.0
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        
        // --- 1. BALANCE SECTION ---
        ConnectedCardGroup {
            ConnectedCard(index = 0, totalItems = 1) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your Balance",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    val amountColor = when {
                        netBalance > 0 -> BalancePositive
                        netBalance < 0 -> BalanceNegative
                        else -> MaterialTheme.colorScheme.onSurface
                    }
                    
                    val prefix = if (netBalance < 0) "-" else ""
                    
                    Text(
                        text = "$prefix${formatAmount(netBalance)}",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontFamily = RobotoFlex,
                            fontWeight = FontWeight.Medium
                        ),
                        color = amountColor
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    val descriptor = when {
                        netBalance > 0 -> "overall you're owed"
                        netBalance < 0 -> "overall you owe"
                        else -> "you're all settled up"
                    }
                    
                    Text(
                        text = descriptor,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // --- 2. STATS SECTION ---
        ConnectedCardGroup {
            ConnectedCard(index = 0, totalItems = 1) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total people tracked",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = people.size.toString(),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Active balances",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = activeBalancesCount.toString(),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }

        // --- 3. PREMIUM SETTINGS (GROUPED) ---
        ConnectedCardGroup {
            // Setting 1 (Top)
            ConnectedCard(
                index = 0,
                totalItems = 2,
                onClick = { }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Auto Reminders",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = "Premium",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        imageVector = Icons.Rounded.Lock,
                        contentDescription = "Premium Feature",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Setting 2 (Bottom)
            ConnectedCard(
                index = 1,
                totalItems = 2,
                onClick = { }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Export History",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = "Premium",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        imageVector = Icons.Rounded.Lock,
                        contentDescription = "Premium Feature",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // --- 4. PREMIUM BANNER ---
        ConnectedCardGroup {
            ConnectedCard(
                index = 0,
                totalItems = 1,
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Settlr Premium",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Cloud sync, auto reminders & more",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Button(
                        onClick = { },
                        enabled = false
                    ) {
                        Text("Coming Soon")
                    }
                }
            }
        }
    }
}
