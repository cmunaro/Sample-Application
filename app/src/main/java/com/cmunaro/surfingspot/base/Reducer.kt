package com.cmunaro.surfingspot.base

import androidx.databinding.ViewDataBinding

interface Reducer<S : State<B>, C: StateChange, B: ViewDataBinding> {
    fun reduce(oldState: S, stateChange: C): State<B> {
        return oldState
    }
}
