package com.cmunaro.surfingspot.base

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
abstract class BaseViewModel<S : State<B>, SC : StateChange, I: Intent, B: ViewDataBinding> : ViewModel() {
    abstract val reducer: Reducer<S, SC, B>
    abstract val state: StateFlow<S>
    protected abstract val intentChannel: Channel<I>
}