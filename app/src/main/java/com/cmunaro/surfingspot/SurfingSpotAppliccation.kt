package com.cmunaro.surfingspot

import android.app.Application
import com.cmunaro.surfingspot.data.datasource.ImagesDataSource
import com.cmunaro.surfingspot.data.datasource.NumberDataSource
import com.cmunaro.surfingspot.data.datasource.local.CitiesLocalDataSource
import com.cmunaro.surfingspot.data.datasource.remote.CitiesRemoteDataSource
import com.cmunaro.surfingspot.data.datasource.remote.ImagesRemoteDataSource
import com.cmunaro.surfingspot.data.datasource.remote.NumberRemoteDataSource
import com.cmunaro.surfingspot.data.retrofit.NetworkModule
import com.cmunaro.surfingspot.data.room.MeteoDatabase
import com.cmunaro.surfingspot.service.CitiesDataSourceService
import com.cmunaro.surfingspot.service.CitiesDataSourceServiceImpl
import com.cmunaro.surfingspot.service.MeteoService
import com.cmunaro.surfingspot.service.MeteoServiceImpl
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class SurfingSpotApplication : Application() {
    override fun onCreate(){
        super.onCreate()
        startKoin {
            androidContext(this@SurfingSpotApplication)
            modules(koinModule)
        }
    }
}