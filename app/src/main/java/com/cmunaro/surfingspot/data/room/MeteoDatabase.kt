package com.cmunaro.surfingspot.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cmunaro.surfingspot.data.room.dao.MeteoDao
import com.cmunaro.surfingspot.data.room.entity.City

@Database(entities = [City::class], version = 1, exportSchema = false)
abstract class MeteoDatabase : RoomDatabase() {
    abstract fun meteoDao(): MeteoDao

    companion object {
        @Volatile
        private var INSTANCE: MeteoDatabase? = null

        fun getDatabase(context: Context): MeteoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.inMemoryDatabaseBuilder(
                    context.applicationContext,
                    MeteoDatabase::class.java
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
