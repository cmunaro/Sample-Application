package com.cmunaro.surfingspot.data.datasource.remote

import com.cmunaro.surfingspot.data.Resource
import com.cmunaro.surfingspot.data.datasource.ImagesDataSource
import com.cmunaro.surfingspot.data.retrofit.ImageServiceAPI
import com.cmunaro.surfingspot.data.retrofit.objects.ImageCharacteristics
import com.cmunaro.surfingspot.data.retrofit.objects.ImageOrientation
import com.cmunaro.surfingspot.data.retrofit.objects.ImageType
import com.cmunaro.surfingspot.data.retrofit.objects.Order
import kotlinx.coroutines.flow.Flow
import org.koin.core.KoinComponent
import org.koin.core.inject

class ImagesRemoteDataSource : RemoteDataSource(), ImagesDataSource, KoinComponent {
    private val imageServiceAPI: ImageServiceAPI by inject()

    override suspend fun getImageOf(query: String): Flow<Resource<ImageCharacteristics>> {
        return fetch({
            imageServiceAPI.searchImages(
                query,
                "18272859-d179e212c99b362032de166fb",
                20,
                Order.LATEST,
                ImageType.ALL,
                false,
                ImageOrientation.HORIZONTAL,
                0,
                true
            )
        }, { result ->
            result.data?.hits
                ?.maxByOrNull { it.width.toFloat() / it.height.toFloat() }
                ?: ImageCharacteristics(null)
        })
    }
}