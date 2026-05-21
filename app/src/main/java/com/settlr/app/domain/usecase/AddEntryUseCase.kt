package com.settlr.app.domain.usecase

import com.settlr.app.data.model.Entry
import com.settlr.app.data.repository.SettlrRepository
import kotlinx.coroutines.flow.first

class AddEntryUseCase(private val repository: SettlrRepository) {
    suspend operator fun invoke(entry: Entry) {
        val currentEntries = repository.getEntries().first()
        val updatedEntries = currentEntries + entry
        repository.saveEntries(updatedEntries)
    }
}
