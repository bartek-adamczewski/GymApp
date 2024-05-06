package edu.put.gymapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.put.gymapp.database.entities.ExerciseEntity
import edu.put.gymapp.repository.Repository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(State(emptyList(), null))
    val uiState: StateFlow<State> = _uiState.asStateFlow()
    private val eventChannel = Channel<Event>(Channel.CONFLATED)
    val events = eventChannel.receiveAsFlow()

    init {
        getExercises()
    }

    fun getExercises() {
        viewModelScope.launch {
            repository.getTrails().collect { exercises ->
                _uiState.update { state ->
                    state.copy(exercises = exercises)
                }
            }
        }
    }

    fun addExercise(exercise: ExerciseEntity) {
        viewModelScope.launch {
            repository.insertAll(listOf(exercise))
            getExercises()
        }
    }


    data class State(
        val exercises: List<ExerciseEntity>,
        val selectedExercise: ExerciseEntity?,
    ) {
        companion object {
            val DEFAULT = State(
                exercises = emptyList(),
                selectedExercise = null,
            )
        }
    }

    sealed class Event {
        object ExerciseAdded : Event()
        data class Error(val message: String) : Event()
    }
}