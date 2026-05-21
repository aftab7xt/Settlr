package com.settlr.app.data.repository

import com.settlr.app.data.local.SettlrDataStore
import com.settlr.app.data.model.Entry
import com.settlr.app.data.model.Person
import kotlinx.coroutines.flow.Flow

class SettlrRepository(private val dataStore: SettlrDataStore) {

    fun getPeople(): Flow<List<Person>> {
        return dataStore.getPeople()
    }

    fun getEntries(): Flow<List<Entry>> {
        return dataStore.getEntries()
    }

    suspend fun savePeople(people: List<Person>) {
        dataStore.savePeople(people)
    }

    suspend fun saveEntries(entries: List<Entry>) {
        dataStore.saveEntries(entries)
    }
}
