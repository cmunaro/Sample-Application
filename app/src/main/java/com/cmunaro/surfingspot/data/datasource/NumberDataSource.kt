package com.cmunaro.surfingspot.data.datasource

import com.cmunaro.surfingspot.data.Resource
import kotlinx.coroutines.flow.Flow

interface NumberDataSource {
    suspend fun getNumber(): Flow<Resource<Int>>
}