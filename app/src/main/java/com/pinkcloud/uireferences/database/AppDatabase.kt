package com.pinkcloud.uireferences.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Mission::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val missionDatabaseDao: MissionDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance =
                        Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                            .fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}