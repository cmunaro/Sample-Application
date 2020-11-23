package com.cmunaro.surfingspot.data.datasource.local

import com.cmunaro.surfingspot.data.Resource
import com.cmunaro.surfingspot.data.datasource.CitiesDataSource
import com.cmunaro.surfingspot.data.room.dao.MeteoDao
import com.cmunaro.surfingspot.data.room.entity.City
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import org.koin.core.KoinComponent
import org.koin.core.inject

class CitiesLocalDataSource : CitiesDataSource, KoinComponent {
    private val meteoDao: MeteoDao by inject()

    override suspend fun getCities() = flow {
        emit(Resource.Loading())
        emitAll(meteoDao.getCitiesMeteo().mapNotNull { Resource.Success(it) })
    }

    override suspend fun insertCities(cities: List<City>) {
        cities.forEach { meteoDao.insert(it) }
    }
}