package com.cmunaro.surfingspot.service

import com.cmunaro.surfingspot.data.Resource
import com.cmunaro.surfingspot.data.room.entity.City
import kotlinx.coroutines.flow.Flow

interface CitiesDataSourceService {
    suspend fun getCities(shouldWaitRemoteResult: Boolean=true): Flow<Resource<List<City>>>
}