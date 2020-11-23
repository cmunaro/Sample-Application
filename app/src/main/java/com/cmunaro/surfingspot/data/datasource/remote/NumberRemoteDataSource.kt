package com.cmunaro.surfingspot.data.datasource.remote

import com.cmunaro.surfingspot.data.Resource
import com.cmunaro.surfingspot.data.datasource.NumberDataSource
import com.cmunaro.surfingspot.data.retrofit.NumberServiceAPI
import kotlinx.coroutines.flow.Flow
import org.koin.core.KoinComponent
import org.koin.core.inject

class NumberRemoteDataSource : RemoteDataSource(), NumberDataSource, KoinComponent {
    private val numberAPI: NumberServiceAPI by inject()

    override suspend fun getNumber(): Flow<Resource<Int>> {
        return fetch(numberAPI::getNumber) { result ->
            result.data?.substringBefore(" ")?.toIntOrNull() ?: 0
        }
    }
}