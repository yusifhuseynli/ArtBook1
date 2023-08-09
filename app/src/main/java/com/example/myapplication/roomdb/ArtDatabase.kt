package com.example.myapplication.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Art::class], version = 2)
abstract class ArtDatabase:RoomDatabase() {
    abstract fun artDao():ArtDao

}