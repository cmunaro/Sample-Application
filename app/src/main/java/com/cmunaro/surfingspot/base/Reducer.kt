package com.cmunaro.surfingspot.base

import androidx.databinding.ViewDataBinding
import com.cmunaro.surfingspot.home.HomeFragment
import com.cmunaro.surfingspot.home.HomeState

interface Reducer<S : State<B>, C: StateChange, B: ViewDataBinding> {
    fun reduce(oldState: S, stateChange: C): State<B> {
        return oldState
    }


    fun reduceV2(intent: Intent, oldState: S): State<B> {
        return oldState
    }
}
