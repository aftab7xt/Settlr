package com.settlr.app.di

import com.settlr.app.data.local.SettlrDataStore
import com.settlr.app.data.repository.SettlrRepository
import com.settlr.app.domain.usecase.AddEntryUseCase
import com.settlr.app.domain.usecase.GetPeopleWithBalancesUseCase
import com.settlr.app.domain.usecase.ParseNaturalLanguageUseCase
import com.settlr.app.domain.usecase.SettleUpUseCase
import com.settlr.app.ui.screens.activity.ActivityViewModel
import com.settlr.app.ui.screens.contact_detail.ContactDetailViewModel
import com.settlr.app.ui.screens.people.PeopleViewModel
import com.settlr.app.ui.screens.quickadd.QuickAddViewModel
import com.settlr.app.ui.screens.you.YouViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { SettlrDataStore(androidContext()) }
    single { SettlrRepository(get()) }
    single { GetPeopleWithBalancesUseCase(get()) }
    single { AddEntryUseCase(get()) }
    single { SettleUpUseCase(get()) }
    single { ParseNaturalLanguageUseCase() }
    
    viewModel { PeopleViewModel(get()) }
    viewModel { ActivityViewModel(get()) }
    viewModel { YouViewModel(get()) }
    viewModel { QuickAddViewModel(get(), get()) }
    viewModel { params -> ContactDetailViewModel(get(), get(), get(), params.get()) }
}
