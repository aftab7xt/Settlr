package com.settlr.app.ui.screens.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settlr.app.domain.usecase.GetPeopleWithBalancesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.math.abs

class PeopleViewModel(
    getPeopleWithBalances: GetPeopleWithBalancesUseCase
) : ViewModel() {

    private val rawFlow = getPeopleWithBalances()

    val peopleWithBalances = rawFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val totalOwedToMe = rawFlow.map { list ->
        list.filter { it.balance > 0 }.sumOf { it.balance }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )

    val totalYouOwe = rawFlow.map { list ->
        list.filter { it.balance < 0 }.sumOf { abs(it.balance) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )
}
