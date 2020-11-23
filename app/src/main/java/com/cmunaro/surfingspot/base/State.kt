package com.cmunaro.surfingspot.base

import androidx.databinding.ViewDataBinding
import com.cmunaro.surfingspot.R
import com.cmunaro.surfingspot.data.Resource
import com.google.android.material.snackbar.Snackbar

interface State<B : ViewDataBinding> {
    fun render(binding: B)

    fun <GEN_B> render(binding: GEN_B) {
        try {
            @Suppress("unchecked_cast")
            render(binding as B)
        } catch (exception: Exception) {
        }
    }

    class NetworkError<B : ViewDataBinding>(private val resource: Resource.Error<*>) : State<B> {
        override fun render(binding: B) {
            if (resource.exception != null) {
                Snackbar.make(binding.root, R.string.network_error, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
