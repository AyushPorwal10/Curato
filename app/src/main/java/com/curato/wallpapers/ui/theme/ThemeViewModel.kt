package com.curato.wallpapers.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

// Provides the ViewModel anywhere in the tree without prop-drilling
val LocalThemeViewModel = staticCompositionLocalOf<ThemeViewModel> {
    error("LocalThemeViewModel not provided")
}

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : ViewModel() {

    companion object {
        private val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    }

    // null  → not yet set by user, follow system preference
    // true  → user explicitly chose dark
    // false → user explicitly chose light
    val isDarkTheme: StateFlow<Boolean?> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { it[IS_DARK_THEME] }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun toggle(currentIsDark: Boolean) {
        viewModelScope.launch {
            dataStore.edit { it[IS_DARK_THEME] = !currentIsDark }
        }
    }
}
