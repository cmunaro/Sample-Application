package com.cmunaro.surfingspot.data.retrofit

import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface NumberServiceAPI {
    @GET("/random/math")
    suspend fun getNumber(): ApiResponse<String>
}