package com.settlr.app.ui.screens.people

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.settlr.app.ui.components.transaction.*
import com.settlr.app.ui.components.feature.*
import com.settlr.app.ui.components.core.*
import com.settlr.app.util.formatTimestamp
import org.koin.androidx.compose.koinViewModel

@Composable
fun PeopleScreen(
    onPersonClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PeopleViewModel = koinViewModel()
) {
    val peopleWithBalances by viewModel.peopleWithBalances.collectAsState()
    val totalOwedToMe by viewModel.totalOwedToMe.collectAsState()
    val totalYouOwe by viewModel.totalYouOwe.collectAsState()

    if (peopleWithBalances.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Groups,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No one here yet",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tap + to log your first entry",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 88.dp)
        ) {
            item {
                SummaryHeader(
                    totalOwedToMe = totalOwedToMe,
                    totalYouOwe = totalYouOwe
                )
            }
            itemsIndexed(
                items = peopleWithBalances,
                key = { _, item -> item.person.id }
            ) { index, item ->
                ConnectedCard(
                    index = index,
                    totalItems = peopleWithBalances.size,
                    onClick = { onPersonClick(item.person.id) },
                    modifier = Modifier
                        .animateItem()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = if (index < peopleWithBalances.size - 1) 2.dp else 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PersonAvatar(
                            name = item.person.name,
                            avatarColor = item.person.avatarColor,
                            size = 48.dp
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = item.person.name,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = formatTimestamp(item.lastEntryTimestamp),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        BalanceChip(amount = item.balance)
                    }
                }
            }
        }
    }
}
