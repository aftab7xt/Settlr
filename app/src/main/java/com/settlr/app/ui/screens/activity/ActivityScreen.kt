package com.settlr.app.ui.screens.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.settlr.app.ui.components.EntryCard
import com.settlr.app.util.formatTimestamp
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActivityScreen(
    modifier: Modifier = Modifier,
    viewModel: ActivityViewModel = koinViewModel()
) {
    val entries by viewModel.entries.collectAsState()
    val people by viewModel.people.collectAsState()

    if (entries.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No activity yet",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    } else {
        val personMap = people.associate { it.id to it.name }
        val groupedEntries = entries
            .sortedByDescending { it.timestamp }
            .groupBy { formatTimestamp(it.timestamp) }

        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            groupedEntries.forEach { (dateHeader, dateEntries) ->
                item {
                    Text(
                        text = dateHeader,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
                
                items(
                    items = dateEntries,
                    key = { it.id }
                ) { entry ->
                    EntryCard(
                        entry = entry,
                        personName = personMap[entry.personId] ?: "Unknown",
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}
