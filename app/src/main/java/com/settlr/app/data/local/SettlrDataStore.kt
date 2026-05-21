package com.settlr.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.settlr.app.data.model.Entry
import com.settlr.app.data.model.Person
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settlr_prefs")

class SettlrDataStore(private val context: Context) {

    private val peopleKey = stringPreferencesKey("people_list")
    private val entriesKey = stringPreferencesKey("entries_list")

    fun getPeople(): Flow<List<Person>> {
        return context.dataStore.data.map { preferences ->
            val jsonString = preferences[peopleKey] ?: "[]"
            Json.decodeFromString(jsonString)
        }
    }

    fun getEntries(): Flow<List<Entry>> {
        return context.dataStore.data.map { preferences ->
            val jsonString = preferences[entriesKey] ?: "[]"
            Json.decodeFromString(jsonString)
        }
    }

    suspend fun savePeople(people: List<Person>) {
        context.dataStore.edit { preferences ->
            preferences[peopleKey] = Json.encodeToString(people)
        }
    }

    suspend fun saveEntries(entries: List<Entry>) {
        context.dataStore.edit { preferences ->
            preferences[entriesKey] = Json.encodeToString(entries)
        }
    }
}
