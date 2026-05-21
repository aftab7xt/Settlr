package com.settlr.app.ui.screens.people

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.settlr.app.ui.components.BalanceChip
import com.settlr.app.ui.components.PersonAvatar
import com.settlr.app.util.formatTimestamp
import org.koin.androidx.compose.koinViewModel

@Composable
fun PeopleScreen(
    onPersonClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PeopleViewModel = koinViewModel()
) {
    val peopleWithBalances by viewModel.peopleWithBalances.collectAsState()

    if (peopleWithBalances.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Add your first entry to get started",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(
                items = peopleWithBalances,
                key = { it.person.id }
            ) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPersonClick(item.person.id) }
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
