package com.virak.simpletodoapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MyCalendarTask::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): MyCalendarTaskDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_calendar_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}