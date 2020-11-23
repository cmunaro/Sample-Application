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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
abstract class BaseFragment<VM : BaseViewModel<*, *, *, *>, B : ViewDataBinding> : Fragment() {
    abstract val layoutResource: Int
    abstract val viewModelClass: Class<VM>
    lateinit var viewModel: VM
    lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(viewModelClass)
        binding = DataBindingUtil.inflate(inflater, layoutResource, container, false)
        setupUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
    }

    abstract fun setupUI()

    private fun observeState() {
        viewModel.state
            .onEach { it.render(binding) }
            .launchIn(lifecycleScope)
    }
}
