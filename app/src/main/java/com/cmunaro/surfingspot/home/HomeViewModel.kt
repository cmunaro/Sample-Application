package com.cmunaro.surfingspot.home

import androidx.lifecycle.viewModelScope
import com.cmunaro.surfingspot.base.BaseViewModel
import com.cmunaro.surfingspot.base.Reducer
import com.cmunaro.surfingspot.data.Resource
import com.cmunaro.surfingspot.databinding.HomeFragmentBinding
import com.cmunaro.surfingspot.home.HomeFragment.*
import com.cmunaro.surfingspot.home.HomeFragment.HomeIntent.*
import com.cmunaro.surfingspot.service.CitiesDataSourceService
import com.cmunaro.surfingspot.service.MeteoService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

@ExperimentalCoroutinesApi
class HomeViewModel : BaseViewModel<HomeState, HomeStateChange, HomeIntent, HomeFragmentBinding>(), KoinComponent {
    override val reducer: Reducer<HomeState, HomeStateChange, HomeFragmentBinding> by inject()
    override val intentChannel = Channel<HomeIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<HomeState>(HomeState.Idle)
    override val state: StateFlow<HomeState> = _state

    private val cityService: CitiesDataSourceService by inject()
    private val meteoService: MeteoService by inject()
    private var meteoObserverJob: Job? = null

    init {
        viewModelScope.launch {
            handleIntents()
        }
    }

    fun deliverIntent(intent: HomeIntent) = viewModelScope.launch {
        intentChannel.send(intent)
    }

    private suspend fun handleIntents() {
        intentChannel.consumeEach { intent ->
            when (intent) {
                StartMeteoObserving -> {
                    oberveMeteo()
                    startAssignRandomTemperatures()
                }
                StopMeteoObserving -> {
                    stopRandomTemperatures()
                    stopObserver()
                }
            }
        }
    }

    private suspend fun oberveMeteo() {
        stopObserver()
        meteoObserverJob = viewModelScope.launch {
            cityService.getCities().collect { resource ->
                _state.value = when (resource) {
                    is Resource.Success -> reducer.reduce(
                        _state.value,
                        HomeStateChange.NewCities(resource.data)
                    )
                    is Resource.Error -> reducer.reduce(
                        _state.value,
                        HomeStateChange.NetworkError(resource)
                    )
                    is Resource.Loading -> reducer.reduce(
                        _state.value,
                        HomeStateChange.LoadingCities
                    )
                } as HomeState
            }
        }
    }

    private suspend fun stopObserver() {
        meteoObserverJob?.cancelAndJoin()
        meteoObserverJob = null
    }

    private fun startAssignRandomTemperatures() {
        meteoService.startGettingMeteo()
    }

    private fun stopRandomTemperatures() {
        meteoService.stopGettingMeteo()
    }
}
