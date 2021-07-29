package com.day.timer.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface PreferenceRepository {

    suspend fun getUserDeathTime(): Flow<Long?>
    suspend fun setUserDeathTime(deathTime: Long)
}

class PreferenceRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
) : PreferenceRepository {

    private val Context._dataStore: DataStore<Preferences> by preferencesDataStore(APP_PREFERENCES)
    private val dataStore: DataStore<Preferences> = context._dataStore

    override suspend fun getUserDeathTime(): Flow<Long?> {
        return dataStore.data.map { it[USER_DEATH_TIME] }
    }

    override suspend fun setUserDeathTime(deathTime: Long) {
        dataStore.edit { it[USER_DEATH_TIME] = deathTime }
    }

    companion object {
        const val APP_PREFERENCES = "mortality_preferences"
        val USER_DEATH_TIME = longPreferencesKey("user_death_time")
    }

}