package com.cmunaro.surfingspot.data.datasource

import com.cmunaro.surfingspot.data.Resource
import com.cmunaro.surfingspot.data.retrofit.objects.ImageCharacteristics
import kotlinx.coroutines.flow.Flow

interface ImagesDataSource {
    suspend fun getImageOf(query: String) : Flow<Resource<ImageCharacteristics>>
}