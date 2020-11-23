package com.cmunaro.surfingspot.data.datasource

import com.cmunaro.surfingspot.data.Resource
import com.cmunaro.surfingspot.data.room.entity.City
import kotlinx.coroutines.flow.Flow

interface CitiesDataSource {
    suspend fun getCities() : Flow<Resource<List<City>>>
    suspend fun insertCities(cities: List<City>)
}