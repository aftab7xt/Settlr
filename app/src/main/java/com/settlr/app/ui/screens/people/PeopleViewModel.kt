package com.settlr.app.ui.screens.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settlr.app.domain.usecase.GetPeopleWithBalancesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class PeopleViewModel(
    getPeopleWithBalances: GetPeopleWithBalancesUseCase
) : ViewModel() {

    val peopleWithBalances = getPeopleWithBalances().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
}
