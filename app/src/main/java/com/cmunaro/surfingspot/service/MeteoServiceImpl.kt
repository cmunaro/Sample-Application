package com.cmunaro.surfingspot.service

import com.cmunaro.surfingspot.data.Resource
import com.cmunaro.surfingspot.data.datasource.NumberDataSource
import com.cmunaro.surfingspot.data.room.dao.MeteoDao
import com.cmunaro.surfingspot.data.room.entity.City
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*
import kotlin.concurrent.schedule

class MeteoServiceImpl : MeteoService, KoinComponent {
    private val meteoDao: MeteoDao by inject()
    private val numberDataSource: NumberDataSource by inject()
    private var timerTask: TimerTask? = null

    private suspend fun updateRandomCityTemperature() {
        numberDataSource.getNumber().collect { resource ->
            if (resource is Resource.Success) {
                val temperature = resource.data % City.MAX_TEMP
                meteoDao.updateTemperature(temperature)
            }
        }
    }

    override fun startGettingMeteo() {
        stopGettingMeteo()
        timerTask = Timer().schedule(0, TEMPERATURE_UPDATE_DELAY) {
            runBlocking { updateRandomCityTemperature() }
        }
    }

    override fun stopGettingMeteo() {
        timerTask?.cancel()
        timerTask = null
    }

    companion object {
        const val TEMPERATURE_UPDATE_DELAY = 3000L
    }
}