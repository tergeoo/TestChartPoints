package com.tergeo.testcoordinatepoints.presentation.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

interface SideEffect

sealed interface BaseEffect : SideEffect {

    data class MessageEffect(val message: String) : BaseEffect
}

open class BaseViewModel<State>(initialState: State) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialState)

    private val _effect = Channel<SideEffect>()
    val effects = _effect.receiveAsFlow()

    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleException(throwable)
    }

    open fun handleException(throwable: Throwable) {
        viewModelScope.launch {
            sendEffect(
                BaseEffect.MessageEffect(
                    throwable.message ?: throwable.toString()
                )
            )
        }
    }

    protected fun setState(reduce: State.() -> State) {
        _state.value = reduce.invoke(_state.value)
    }

    protected suspend fun sendEffect(effect: SideEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}