package com.cmunaro.surfingspot.data.retrofit

import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(RequestInterceptor())
        .build()

    private fun getRetrofitFor(url: String) = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
        .build()

    val citiesServiceAPI: CitiesServiceAPI =
        getRetrofitFor("https://run.mocky.io").create(CitiesServiceAPI::class.java)
    val numberAPI: NumberServiceAPI =
        getRetrofitFor("http://numbersapi.com").create(NumberServiceAPI::class.java)
    val imageServiceAPI: ImageServiceAPI =
        getRetrofitFor("https://pixabay.com").create(ImageServiceAPI::class.java)
}
