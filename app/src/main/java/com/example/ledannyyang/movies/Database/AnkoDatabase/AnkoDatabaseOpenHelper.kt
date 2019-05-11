package com.example.ledannyyang.movies.Database.AnkoDatabase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.ledannyyang.movies.Model.Movie
import org.jetbrains.anko.db.*

class AnkoDatabaseOpenHelper private constructor(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "KinoDatabase", null, 1) {



    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(Movie.TABLE_NAME, true,
                Movie.MOVIE_ID to INTEGER + PRIMARY_KEY,
                Movie.COLUMN_TITLE to TEXT,
                Movie.COLUMN_GENRE to TEXT,
                Movie.COLUMN_SCORE to TEXT,
                Movie.COLUMN_YEAR to TEXT,
                Movie.COLUMN_POSTERPATH to TEXT)
        Log.d("APIQUERY", "Creating table ${Movie.TABLE_NAME}")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Movie.TABLE_NAME, true)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


}

// Access property for Context
//val Context.database: AnkoDatabaseOpenHelper
//    get() = AnkoDatabaseOpenHelper