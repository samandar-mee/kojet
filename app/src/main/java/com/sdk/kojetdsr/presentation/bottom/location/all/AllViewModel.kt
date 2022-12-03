package com.sdk.kojetdsr.presentation.bottom.location.all

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.domain.use_cases.AllUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllViewModel @Inject constructor(
    private val useCases: AllUseCases
) : ViewModel() {
    private val _state: MutableState<AllState> = mutableStateOf(AllState())
    val state: State<AllState> get() = _state

    init {
        getAllLocationNames()
    }

    private fun getAllLocationNames() {
        viewModelScope.launch {
            useCases.getLocationNamesUseCase("")
                .onStart {
                    _state.value = _state.value.copy(isLoading = true)
                    delay(600L)
                }
                .catch {
                    _state.value =
                        _state.value.copy(error = it.stackTraceToString(), isLoading = false)
                }
                .collect {
                    _state.value = _state.value.copy(isLoading = false, success = it)
                }
        }
    }

    fun onEvent(event: AllEvent) {
        when (event) {
            is AllEvent.OnFavoriteClick -> {
                viewModelScope.launch {
                    useCases.updateFavLocationName(event.name)
                }
            }
            is AllEvent.OnItemClick -> {

            }
        }
    }
}