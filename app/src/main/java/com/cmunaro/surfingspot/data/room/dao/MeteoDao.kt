package com.cmunaro.surfingspot.data.room.dao

import androidx.room.*
import com.cmunaro.surfingspot.data.room.entity.City
import kotlinx.coroutines.flow.Flow

@Dao
interface MeteoDao {

    @Query("SELECT * FROM city ORDER BY temperature DESC")
    fun getCitiesMeteo(): Flow<List<City>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: City)

    @Query("UPDATE CITY SET temperature = :temperature WHERE name = :cityName")
    fun updateTemperature(temperature: Int, cityName: String? = getRandomCity()?.name)

    @Query("SELECT * from City ORDER BY RANDOM() LIMIT 1")
    fun getRandomCity(): City?
}