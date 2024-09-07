package co.kecker.zilch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Home("Hello"))
    val uiState: StateFlow<UiState> get() = _uiState
    
    fun updateHomeData(data: String) = viewModelScope.launch {
        _uiState.emit(UiState.Home(data))
    }
}

sealed interface UiState {
    data class Home(val data: String): UiState
}