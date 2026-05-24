package com.settlr.app.ui.screens.you

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settlr.app.data.repository.SettlrRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class YouViewModel(private val repository: SettlrRepository) : ViewModel() {

    val people = repository.getPeople().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val entries = repository.getEntries().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val totalOwedToMe: StateFlow<Double> = entries.map { entriesList ->
        entriesList.filter { !it.isSettled && it.isOwedToMe }.sumOf { it.amount }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )

    val totalYouOwe: StateFlow<Double> = entries.map { entriesList ->
        entriesList.filter { !it.isSettled && !it.isOwedToMe }.sumOf { it.amount }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )

    val netBalance: StateFlow<Double> = combine(people, entries) { _, currentEntries ->
        var total = 0.0
        currentEntries.filter { !it.isSettled }.forEach { entry ->
            if (entry.isOwedToMe) {
                total += entry.amount
            } else {
                total -= entry.amount
            }
        }
        total
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )
}
