package com.cmunaro.surfingspot

import com.cmunaro.surfingspot.base.Reducer
import com.cmunaro.surfingspot.data.Resource
import com.cmunaro.surfingspot.data.room.entity.City
import com.cmunaro.surfingspot.home.HomeIntent
import com.cmunaro.surfingspot.home.HomeState
import com.cmunaro.surfingspot.home.HomeStateChange
import com.cmunaro.surfingspot.home.HomeViewModel
import com.cmunaro.surfingspot.service.CitiesDataSourceService
import com.cmunaro.surfingspot.service.MeteoService
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.mock.declareMock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class HomeViewModelTest : KoinTest {
    private lateinit var reducer: Reducer<HomeState, HomeStateChange>
    private lateinit var viewModel: HomeViewModel
    private lateinit var meteoService: MeteoService
    private lateinit var citiesService: CitiesDataSourceService
    private val citiesListFlow = MutableStateFlow<Resource<List<City>>>(Resource.Loading())

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setup() = runBlocking {
        startKoin { modules(koinModule) }
        reducer = declareMock { }
        citiesService = declareMock()
        meteoService = declareMock { }
        whenever(citiesService.getCities(true)).thenReturn(citiesListFlow)
        whenever(reducer.reduce(anyOrNull(), anyOrNull())).thenReturn(HomeState.Idle)
        viewModel = HomeViewModel()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun manageIntents() = runBlockingTest {
        viewModel.deliverIntent(HomeIntent.StartMeteoObserving)
        viewModel.deliverIntent(HomeIntent.StopMeteoObserving)

        verify(meteoService).startGettingMeteo()
        verify(citiesService).getCities(true)
        verify(meteoService).stopGettingMeteo()
    }

    @Test
    fun deliverSuccessStateChange() = runBlockingTest {
        viewModel.deliverIntent(HomeIntent.StartMeteoObserving)
        val cityList = listOf(City("Cuba"))

        citiesListFlow.value = Resource.Success(cityList)

        verify(reducer).reduce(isA(), isA<HomeStateChange.LoadingCities>())
        verify(reducer).reduce(isA(), eq(HomeStateChange.NewCities(cityList)))
    }

    @Test
    fun deliverErrorStateChange() = runBlockingTest {
        viewModel.deliverIntent(HomeIntent.StartMeteoObserving)

        citiesListFlow.value = Resource.Error()

        verify(reducer).reduce(isA(), isA<HomeStateChange.LoadingCities>())
        verify(reducer).reduce(isA(), isA<HomeStateChange.NetworkError>())
    }
}