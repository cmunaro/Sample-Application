package com.cmunaro.surfingspot.home

import android.view.View
import com.cmunaro.surfingspot.R
import com.cmunaro.surfingspot.base.State
import com.cmunaro.surfingspot.base.StateChange
import com.cmunaro.surfingspot.data.Resource
import com.cmunaro.surfingspot.data.room.entity.City
import com.cmunaro.surfingspot.databinding.HomeFragmentBinding
import com.cmunaro.surfingspot.home.recycleradapter.MeteoRecyclerAdapter
import com.google.android.material.snackbar.Snackbar

sealed class HomeState : State<HomeFragmentBinding> {
    object Idle : HomeState() {
        override fun render(binding: HomeFragmentBinding) {
            (binding.citiesRecycler.adapter as MeteoRecyclerAdapter)
                .setItems(emptyList())
            hideLoading(binding)
        }
    }

    object Loading : HomeState() {
        override fun render(binding: HomeFragmentBinding) {
            showLoading(binding)
        }
    }

    data class ShowCities(val cities: List<City>) : HomeState() {
        override fun render(binding: HomeFragmentBinding) {
            (binding.citiesRecycler.adapter as MeteoRecyclerAdapter).setItems(cities)
            binding.citiesRecycler.layoutManager?.scrollToPosition(0)
            hideLoading(binding)
        }
    }

    data class NetworkError(val resource: Resource.Error<*>): HomeState() {
        override fun render(binding: HomeFragmentBinding) {
            Snackbar.make(binding.root, R.string.internal_server_error, Snackbar.LENGTH_LONG).show()
        }
    }

    fun showLoading(binding: HomeFragmentBinding) {
        binding.loadingText.visibility = View.VISIBLE
    }

    fun hideLoading(binding: HomeFragmentBinding) {
        binding.loadingText.visibility = View.GONE
    }
}

sealed class HomeStateChange : StateChange {
    data class NewCities(val cities: List<City>) : HomeStateChange()
    object LoadingCities : HomeStateChange()
    data class NetworkError(val resource: Resource.Error<*>) : HomeStateChange(),
        StateChange by StateChange.NetworkError(resource)
}
