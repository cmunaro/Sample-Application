package com.cmunaro.surfingspot.data.retrofit

import com.cmunaro.surfingspot.data.retrofit.objects.Cities
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface CitiesServiceAPI {
    //@GET("/v3/652ceb94-b24e-432b-b6c5-8a54bc1226b6")
    @GET("/v3/df770e31-60bf-4804-bfcd-3119580230f6")
    suspend fun getCities(): ApiResponse<Cities>
}