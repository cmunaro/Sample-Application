package com.cmunaro.surfingspot

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import com.cmunaro.surfingspot.base.BaseViewModel
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before

@ExperimentalCoroutinesApi
open class AppArchitectureVMTest<VM: BaseViewModel<*, *, *>> {
    open lateinit var viewModel: VM
    private val lifecycle = LifecycleRegistry(mock())

    @Before
    @CallSuper
    open fun setup() = runBlocking {
        lifecycle.addObserver(viewModel)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }
}
