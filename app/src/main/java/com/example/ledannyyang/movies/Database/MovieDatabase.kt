package com.example.ledannyyang.movies.Database

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import com.example.ledannyyang.movies.Model.Movie


@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDAO(): MovieDAO
}