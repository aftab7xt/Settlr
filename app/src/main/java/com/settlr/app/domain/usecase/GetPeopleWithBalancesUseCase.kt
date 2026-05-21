package com.settlr.app.domain.usecase

import com.settlr.app.data.model.Person
import com.settlr.app.data.repository.SettlrRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

data class PersonWithBalance(
    val person: Person,
    val balance: Double,
    val lastEntryTimestamp: Long
)

class GetPeopleWithBalancesUseCase(private val repository: SettlrRepository) {
    operator fun invoke(): Flow<List<PersonWithBalance>> {
        return combine(repository.getPeople(), repository.getEntries()) { people, entries ->
            people.map { person ->
                val personEntries = entries.filter { it.personId == person.id }
                val activeEntries = personEntries.filter { !it.isSettled }
                
                var balance = 0.0
                activeEntries.forEach { entry ->
                    if (entry.isOwedToMe) {
                        balance += entry.amount
                    } else {
                        balance -= entry.amount
                    }
                }
                
                val lastTimestamp = personEntries.maxOfOrNull { it.timestamp } ?: person.createdAt
                
                PersonWithBalance(
                    person = person,
                    balance = balance,
                    lastEntryTimestamp = lastTimestamp
                )
            }.sortedByDescending { it.lastEntryTimestamp }
        }
    }
}
