package com.cmunaro.surfingspot.data.datasource.remote

import com.cmunaro.surfingspot.data.Resource
import com.cmunaro.surfingspot.data.datasource.CitiesDataSource
import com.cmunaro.surfingspot.data.datasource.ImagesDataSource
import com.cmunaro.surfingspot.data.retrofit.CitiesServiceAPI
import com.cmunaro.surfingspot.data.room.entity.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.inject

class CitiesRemoteDataSource : RemoteDataSource(), CitiesDataSource, KoinComponent {
    private val citiesServiceAPI: CitiesServiceAPI by inject()
    private val imageDataSource: ImagesDataSource by inject()

    override suspend fun getCities(): Flow<Resource<List<City>>> {
        return fetch(citiesServiceAPI::getCities) { result ->
            val cities = result.data?.cities
            cities?.parallelStream()?.forEach { city ->
                runBlocking {
                    city.assignRandomTemperature()
                    imageDataSource.getImageOf(city.name)
                        .filter { it !is Resource.Loading }
                        .take(1)
                        .collect { result ->
                            if (result is Resource.Success) {
                                city.imageURL = result.data.imageUrl
                            }
                        }
                }
            }
            cities ?: emptyList()
        }
    }

    override suspend fun insertCities(cities: List<City>) {}
}