package com.settlr.app.domain.usecase

import com.settlr.app.data.repository.SettlrRepository
import kotlinx.coroutines.flow.first

class SettleUpUseCase(private val repository: SettlrRepository) {
    suspend operator fun invoke(personId: String) {
        val currentEntries = repository.getEntries().first()
        val updatedEntries = currentEntries.map { entry ->
            if (entry.personId == personId && !entry.isSettled) {
                entry.copy(isSettled = true)
            } else {
                entry
            }
        }
        repository.saveEntries(updatedEntries)
    }
}
