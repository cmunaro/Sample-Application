package com.cmunaro.surfingspot.service

import com.cmunaro.surfingspot.data.datasource.local.CitiesLocalDataSource
import com.cmunaro.surfingspot.data.datasource.remote.CitiesRemoteDataSource
import com.cmunaro.surfingspot.data.datasource.DataSourceService
import com.cmunaro.surfingspot.data.Resource
import com.cmunaro.surfingspot.data.Resource.*
import com.cmunaro.surfingspot.data.room.entity.City
import kotlinx.coroutines.flow.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class CitiesDataSourceServiceImpl : DataSourceService(), CitiesDataSourceService, KoinComponent {
    private val citiesRemoteDataSource: CitiesRemoteDataSource by inject()
    private val citiesLocalDataSource: CitiesLocalDataSource by inject()

    override suspend fun getCities(shouldWaitRemoteResult: Boolean): Flow<Resource<List<City>>> {
        val citiesFlow = getFromDataSource(
            citiesLocalDataSource::getCities,
            citiesRemoteDataSource::getCities,
            citiesLocalDataSource::insertCities,
            shouldWaitRemoteResult
        )
        return citiesFlow.map { resource ->
            if (resource is Success && resource.data.isNullOrEmpty())
                Loading()
            else resource
        }
    }
}