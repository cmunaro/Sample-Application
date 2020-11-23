package com.cmunaro.surfingspot.data.retrofit

import com.cmunaro.surfingspot.data.retrofit.objects.ImageHits
import com.cmunaro.surfingspot.data.retrofit.objects.ImageOrientation
import com.cmunaro.surfingspot.data.retrofit.objects.ImageType
import com.cmunaro.surfingspot.data.retrofit.objects.Order
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageServiceAPI {
    @GET("/api")
    suspend fun  searchImages(
        @Query("q") query: String,
        @Query("key") key: String,
        @Query("per_page") imagesPerPage: Int,
        @Query("order") order: Order,
        @Query("image_type") imageType: ImageType,
        @Query("pretty") prettyJSON: Boolean,
        @Query("orientation") imageOrientation: ImageOrientation,
        @Query("min_width") imageMinWidth: Int,
        @Query("editors_choice") editorChoiseImages: Boolean,
    ): ApiResponse<ImageHits>
}