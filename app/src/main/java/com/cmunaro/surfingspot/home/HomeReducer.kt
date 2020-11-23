package com.cmunaro.surfingspot.home

import com.cmunaro.surfingspot.base.Reducer
import com.cmunaro.surfingspot.base.State
import com.cmunaro.surfingspot.databinding.HomeFragmentBinding

class HomeReducer : Reducer<HomeState, HomeStateChange, HomeFragmentBinding> {
    @Synchronized
    override fun reduce(oldState: HomeState, stateChange: HomeStateChange): State<HomeFragmentBinding> {
        return when(stateChange) {
            is HomeStateChange.NewCities -> HomeState.ShowCities(stateChange.cities)
            HomeStateChange.LoadingCities -> HomeState.Loading
            is HomeStateChange.NetworkError -> {
                if (stateChange.resource.code == 501)
                    HomeState.NetworkError(stateChange.resource)
                else State.NetworkError(stateChange.resource)
            }
        }
    }
}
