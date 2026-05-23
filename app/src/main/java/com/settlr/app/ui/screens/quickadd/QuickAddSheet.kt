package com.settlr.app.ui.screens.quickadd

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.settlr.app.ui.components.transaction.*
import com.settlr.app.ui.components.feature.*
import com.settlr.app.ui.components.core.*
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickAddSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: QuickAddViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val people by viewModel.people.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.isDone) {
        if (state.isDone) {
            onDismiss()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (state.selectedPersonId == null) {
            Text(
                text = "Who is this with?",
                style = MaterialTheme.typography.titleLarge
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(people, key = { it.id }) { person ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.selectPerson(person.id) }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PersonAvatar(
                            name = person.name,
                            avatarColor = person.avatarColor,
                            size = 40.dp
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = person.name,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            var newPersonName by remember { mutableStateOf("") }
            var showPremiumHint by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SuggestionChip(
                    onClick = { showPremiumHint = !showPremiumHint },
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.AutoAwesome,
                            contentDescription = "AI Input"
                        )
                    },
                    label = { Text("Try: Rahul owes me 500") }
                )
                
                if (showPremiumHint) {
                    Text(
                        text = "AI input is a premium feature",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TextField(
                    value = newPersonName,
                    onValueChange = { newPersonName = it },
                    placeholder = { Text("New person's name") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Button(
                    onClick = {
                        scope.launch {
                            viewModel.createNewPerson(newPersonName)
                        }
                    },
                    enabled = newPersonName.isNotBlank() && !state.isLoading
                ) {
                    Text("Add")
                }
            }
        } else {
            val selectedPerson = people.find { it.id == state.selectedPersonId }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.clearSelectedPerson() }) {
                    BackIcon()
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Logging with ${selectedPerson?.name ?: "..."}",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            TextField(
                value = state.amount,
                onValueChange = { viewModel.setAmount(it) },
                textStyle = MaterialTheme.typography.displayMedium.copy(textAlign = TextAlign.Center),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                placeholder = { Text("0", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            AmountChips(
                onAmountSelected = { amount -> viewModel.setAmount(amount.toString()) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                SegmentedButton(
                    selected = state.isOwedToMe,
                    onClick = { if (!state.isOwedToMe) viewModel.toggleDirection() },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
                ) {
                    Text("They owe me")
                }
                SegmentedButton(
                    selected = !state.isOwedToMe,
                    onClick = { if (state.isOwedToMe) viewModel.toggleDirection() },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
                ) {
                    Text("I owe them")
                }
            }

            TextField(
                value = state.note,
                onValueChange = { viewModel.setNote(it) },
                placeholder = { Text("What was this for? (Optional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    scope.launch {
                        viewModel.saveEntry()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.amount.isNotBlank() && !state.isLoading
            ) {
                Text("Save")
            }
        }
    }
}
