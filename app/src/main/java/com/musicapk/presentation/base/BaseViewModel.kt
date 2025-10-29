package com.musicapk.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE : UiState, EVENT : UiEvent, EFFECT : UiEffect>(
    initialState: STATE
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<STATE> = _uiState.asStateFlow()
    
    private val _effect = Channel<EFFECT>()
    val effect = _effect.receiveAsFlow()
    
    protected val currentState: STATE
        get() = _uiState.value
    
    abstract fun onEvent(event: EVENT)
    
    protected fun setState(reducer: STATE.() -> STATE) {
        _uiState.value = currentState.reducer()
    }
    
    protected fun sendEffect(effect: EFFECT) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}

