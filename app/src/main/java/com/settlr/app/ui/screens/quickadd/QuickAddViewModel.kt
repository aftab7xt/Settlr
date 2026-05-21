package com.settlr.app.ui.screens.quickadd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settlr.app.data.model.Entry
import com.settlr.app.data.model.Person
import com.settlr.app.data.repository.SettlrRepository
import com.settlr.app.domain.usecase.AddEntryUseCase
import com.settlr.app.util.formatAmount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.random.Random

data class QuickAddState(
    val selectedPersonId: String? = null,
    val amount: String = "",
    val note: String = "",
    val isOwedToMe: Boolean = true,
    val isLoading: Boolean = false,
    val isDone: Boolean = false
)

class QuickAddViewModel(
    private val addEntry: AddEntryUseCase,
    private val repository: SettlrRepository
) : ViewModel() {

    private val _state = MutableStateFlow(QuickAddState())
    val state: StateFlow<QuickAddState> = _state.asStateFlow()

    val people = repository.getPeople().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun selectPerson(personId: String) {
        _state.update { it.copy(selectedPersonId = personId) }
    }

    fun setAmount(amount: String) {
        _state.update { it.copy(amount = amount) }
    }

    fun setNote(note: String) {
        _state.update { it.copy(note = note) }
    }

    fun toggleDirection() {
        _state.update { it.copy(isOwedToMe = !it.isOwedToMe) }
    }

    suspend fun createNewPerson(name: String) {
        if (name.isBlank()) return
        _state.update { it.copy(isLoading = true) }
        
        val randomColor = 0xFF000000.toInt() or Random.nextInt(0xFFFFFF)
        val newPerson = Person(name = name.trim(), avatarColor = randomColor)
        
        val currentPeople = repository.getPeople().first()
        repository.savePeople(currentPeople + newPerson)
        
        _state.update { 
            it.copy(
                selectedPersonId = newPerson.id,
                isLoading = false
            )
        }
    }

    suspend fun saveEntry() {
        val currentState = _state.value
        val amountDouble = currentState.amount.toDoubleOrNull()
        
        if (amountDouble == null || amountDouble <= 0.0 || currentState.selectedPersonId == null) {
            return
        }

        _state.update { it.copy(isLoading = true) }
        
        val newEntry = Entry(
            personId = currentState.selectedPersonId,
            amount = amountDouble,
            note = currentState.note.trim(),
            isOwedToMe = currentState.isOwedToMe
        )
        
        addEntry(newEntry)
        
        _state.update { it.copy(isLoading = false, isDone = true) }
    }
}
