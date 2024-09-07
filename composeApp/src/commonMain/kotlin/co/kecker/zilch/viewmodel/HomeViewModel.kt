package co.kecker.zilch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.kecker.zilch.data.Score
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Home(listOf()))
    val uiState: StateFlow<UiState> get() = _uiState
    
    fun updateHomeData(data: UiEvent) = viewModelScope.launch {
        when (data) {
           is UiEvent.UpdateScores -> {
               _uiState.emit(
                   UiState.Home(data.scores.sortedByDescending { it.score })
               )
           }

            else -> {}
        }
    }
}

sealed interface UiState {
    data class Home(val data: List<Score>): UiState
}

sealed interface UiEvent {
    data class UpdateScores(val scores: List<Score>): UiEvent
}