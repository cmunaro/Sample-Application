package com.cmunaro.surfingspot

import com.cmunaro.surfingspot.base.Reducer
import com.cmunaro.surfingspot.data.datasource.ImagesDataSource
import com.cmunaro.surfingspot.data.datasource.NumberDataSource
import com.cmunaro.surfingspot.data.datasource.local.CitiesLocalDataSource
import com.cmunaro.surfingspot.data.datasource.remote.CitiesRemoteDataSource
import com.cmunaro.surfingspot.data.datasource.remote.ImagesRemoteDataSource
import com.cmunaro.surfingspot.data.datasource.remote.NumberRemoteDataSource
import com.cmunaro.surfingspot.data.retrofit.NetworkModule
import com.cmunaro.surfingspot.data.room.MeteoDatabase
import com.cmunaro.surfingspot.databinding.HomeFragmentBinding
import com.cmunaro.surfingspot.home.HomeReducer
import com.cmunaro.surfingspot.home.HomeState
import com.cmunaro.surfingspot.home.HomeStateChange
import com.cmunaro.surfingspot.service.CitiesDataSourceService
import com.cmunaro.surfingspot.service.CitiesDataSourceServiceImpl
import com.cmunaro.surfingspot.service.MeteoService
import com.cmunaro.surfingspot.service.MeteoServiceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val koinModule = module {
    single { MeteoDatabase.getDatabase(androidContext()).meteoDao() }
    single { NetworkModule.citiesServiceAPI }
    single { NetworkModule.numberAPI }
    single { NetworkModule.imageServiceAPI }
    single { CitiesRemoteDataSource() }
    single { CitiesLocalDataSource() }
    single<CitiesDataSourceService> { CitiesDataSourceServiceImpl() }
    single<MeteoService> { MeteoServiceImpl() }
    single<NumberDataSource> { NumberRemoteDataSource() }
    single<ImagesDataSource> { ImagesRemoteDataSource() }
    single<Reducer<HomeState, HomeStateChange, HomeFragmentBinding>> { HomeReducer() }
}