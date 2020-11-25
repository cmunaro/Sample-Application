package com.cmunaro.surfingspot.base

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
abstract class BaseViewModel<I: Intent, S : State<*>, SC : StateChange> :
    ViewModel() {
    @VisibleForTesting
    abstract val reducer: Reducer<*, *, *>
    @VisibleForTesting
    abstract val state: StateFlow<S>
    @VisibleForTesting
    abstract val intentChannel: Channel<I>

    fun startObservingIntents() {
        viewModelScope.launch {
            intentChannel.consumeEach { intent ->
                handleIntent(intent)
            }
        }
    }

    protected abstract suspend fun handleIntent(intent: I)
}