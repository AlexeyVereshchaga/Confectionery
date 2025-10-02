package com.sun.confectionery.core.mvi


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : BaseState, I : BaseIntent, E : BaseEvent>(initialState: S) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<E>()
    val events = _events.asSharedFlow()

    protected fun setState(reducer: S.() -> S) {
        viewModelScope.launch { _state.value = _state.value.reducer() }
    }

    protected fun sendEvent(e: E) {
        viewModelScope.launch { _events.emit(e) }
    }

    protected fun launch(block: suspend ()->Unit){
        viewModelScope.launch { block() }
    }
}
