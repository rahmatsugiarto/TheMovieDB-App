package com.gato.movieapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.gato.movieapp.util.Constants.Companion.DEFAULT_SEARCH_TYPE
import com.gato.movieapp.util.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.gato.movieapp.util.Constants.Companion.PREFERENCES_NAME
import com.gato.movieapp.util.Constants.Companion.PREFERENCES_SEARCH_TYPE
import com.gato.movieapp.util.Constants.Companion.PREFERENCES_SEARCH_TYPE_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
        val selectedSearchType = stringPreferencesKey(PREFERENCES_SEARCH_TYPE)
        val selectedSearchTypeId = intPreferencesKey(PREFERENCES_SEARCH_TYPE_ID)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveSearchType(searchType: String, searchTypeId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedSearchType] = searchType
            preferences[PreferenceKeys.selectedSearchTypeId] = searchTypeId
        }
    }


    suspend fun saveBackOnline(backOnline: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.backOnline] = backOnline
        }
    }

    val readSearchType: Flow<SearchType> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            SearchType(
                preferences[PreferenceKeys.selectedSearchType] ?: DEFAULT_SEARCH_TYPE,
                preferences[PreferenceKeys.selectedSearchTypeId] ?: 0
            )
        }


    val readBackOnline: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val backOnline = preferences[PreferenceKeys.backOnline] ?: false
            backOnline
        }

}

data class SearchType(
    val selectedSearchType: String,
    val selectedSearchTypeId: Int
)


