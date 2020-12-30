package com.android.timesheetchecker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.timesheetchecker.data.database.model.UserLocal

@Database(entities = [UserLocal::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userLocalDao(): UserLocalDao
}