package com.cmunaro.surfingspot.data.retrofit

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

internal class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val requestBuilder = originalRequest.newBuilder().url(originalUrl)
        val request = requestBuilder.build()
        Log.d("HTTPCHAIN", "$request")
        return chain.proceed(request)
    }
}