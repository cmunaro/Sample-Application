package com.cmunaro.surfingspot.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
abstract class BaseFragment<I: Intent, VM : BaseViewModel<I, *, *>, B : ViewDataBinding> : Fragment() {
    abstract val layoutResource: Int
    abstract val viewModelClass: Class<VM>
    private lateinit var viewModel: VM
    lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(viewModelClass)
        lifecycle.addObserver(viewModel)
        binding = DataBindingUtil.inflate(inflater, layoutResource, container, false)
        setupUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
    }

    abstract fun setupUI()

    protected fun deliverIntent(intent: I) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.intentChannel.send(intent)
        }
    }

    private fun observeState() {
        viewModel.state
            .onEach { it.render(binding) }
            .launchIn(lifecycleScope)
    }
}
