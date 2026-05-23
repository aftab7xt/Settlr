package com.settlr.app.ui.screens.contact_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.settlr.app.ui.components.feature.*
import com.settlr.app.ui.components.core.*
import com.settlr.app.ui.components.transaction.*
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ContactDetailScreen(
    personId: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ContactDetailViewModel = koinViewModel()
) {
    val person by viewModel.person.collectAsState()
    val entries by viewModel.entries.collectAsState()
    val balance by viewModel.balance.collectAsState()
    
    val scope = rememberCoroutineScope()

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                BackIcon()
            }
            Text(
                text = person?.name ?: "",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            person?.let { p ->
                PersonAvatar(
                    name = p.name,
                    avatarColor = p.avatarColor,
                    size = 64.dp
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = p.name,
                    style = MaterialTheme.typography.titleLarge
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                BalanceChip(amount = balance)
                
                if (balance != 0.0) {
                    Spacer(modifier = Modifier.height(16.dp))
                    FilledTonalButton(
                        onClick = {
                            scope.launch {
                                viewModel.settleUp()
                            }
                        }
                    ) {
                        Text("Settle Up")
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                ConnectedCardGroup(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    entries.forEachIndexed { index, entry ->
                        person?.let { p ->
                            ConnectedCard(
                                index = index,
                                totalItems = entries.size
                            ) {
                                EntryCard(
                                    entry = entry,
                                    personName = p.name
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
