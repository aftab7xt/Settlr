package com.settlr.app.ui.screens.contact_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settlr.app.data.model.Entry
import com.settlr.app.data.model.Person
import com.settlr.app.data.repository.SettlrRepository
import com.settlr.app.domain.usecase.AddEntryUseCase
import com.settlr.app.domain.usecase.SettleUpUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ContactDetailViewModel(
    private val repository: SettlrRepository,
    private val settleUpUseCase: SettleUpUseCase,
    private val addEntryUseCase: AddEntryUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val personId: String = checkNotNull(savedStateHandle.get<String>("personId"))

    val person: StateFlow<Person?> = repository.getPeople()
        .map { people -> people.find { it.id == personId } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val entries: StateFlow<List<Entry>> = repository.getEntries()
        .map { allEntries ->
            allEntries
                .filter { it.personId == personId && !it.isSettled }
                .sortedByDescending { it.timestamp }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val balance: StateFlow<Double> = entries
        .map { personEntries ->
            var total = 0.0
            personEntries.forEach { entry ->
                if (entry.isOwedToMe) total += entry.amount else total -= entry.amount
            }
            total
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    suspend fun settleUp() {
        settleUpUseCase(personId)
    }
}
