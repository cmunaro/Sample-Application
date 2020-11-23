package com.cmunaro.surfingspot.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.math.roundToInt
import kotlin.random.Random

@Entity
data class City(
    @PrimaryKey
    val name: String,
    var temperature: Int? = null,
    var imageURL: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (other !is City) return false
        return name == other.name &&
                temperature == other.temperature &&
                imageURL == other.imageURL
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (temperature ?: 0)
        result = 31 * result + (imageURL?.hashCode() ?: 0)
        return result
    }

    fun assignRandomTemperature() {
        temperature = getRandomTemperature()
    }

    fun isSunny(): Boolean {
        return temperature?: 0 >= SUNNY_TEMPERATURE
    }

    companion object {
        const val MAX_TEMP = 40
        private const val MIN_TEMP = 0
        private const val SUNNY_TEMPERATURE = 30

        private fun getRandomTemperature(): Int {
            return (Random.nextFloat() * (MAX_TEMP - MIN_TEMP)).roundToInt()
        }
    }
}