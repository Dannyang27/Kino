package com.example.ledannyyang.movies.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.ledannyyang.movies.Model.Movie

class WatchlistDBHelper(context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        val DATABASE_VERSION = 2
        val DATABASE_NAME = "Kino.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DBContract.WatchlistEntry.TABLE_NAME + " (" +
                        DBContract.WatchlistEntry.ID + " TEXT PRIMARY KEY," +
                        DBContract.WatchlistEntry.TITLE + " TEXT," +
                        DBContract.WatchlistEntry.GENRES + " TEXT," +
                        DBContract.WatchlistEntry.SCORE + " TEXT," +
                        DBContract.WatchlistEntry.YEAR_RELEASE + " TEXT," +
                        DBContract.WatchlistEntry.POSTER_PATH + " TEXT)"


        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${DBContract.WatchlistEntry.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
        Log.d("APIQUERY", "Creating Database Kino.db")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertMovie(movie: Movie): Boolean {
        val db = writableDatabase

        val values = ContentValues()
        values.put(DBContract.WatchlistEntry.ID, movie.id)
        values.put(DBContract.WatchlistEntry.TITLE, movie.title)
        values.put(DBContract.WatchlistEntry.GENRES, movie.genres)
        values.put(DBContract.WatchlistEntry.SCORE, movie.score)
        values.put(DBContract.WatchlistEntry.YEAR_RELEASE, movie.year)
        values.put(DBContract.WatchlistEntry.POSTER_PATH, movie.posterPath)

        val newRowId = db.insert(DBContract.WatchlistEntry.TABLE_NAME, null, values)


        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteMovie(movieid: Int): Boolean {
        val db = writableDatabase
        val selection = DBContract.WatchlistEntry.ID + " LIKE ?"
        val selectionArgs = arrayOf(movieid.toString())
        db.delete(DBContract.WatchlistEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readWatchlist(): MutableList<Movie> {
        val movies = mutableListOf<Movie>()
        val db = writableDatabase
        var cursor: Cursor?
        try {
            cursor = db.rawQuery("select * from ${DBContract.WatchlistEntry.TABLE_NAME}", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var movieId: Int
        var title: String
        var genres: String
        var score: Double
        var year: String
        var posterPath: String? = null

        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                movieId = cursor.getInt(cursor.getColumnIndex(DBContract.WatchlistEntry.ID))
                title = cursor.getString(cursor.getColumnIndex(DBContract.WatchlistEntry.TITLE))
                genres = cursor.getString(cursor.getColumnIndex(DBContract.WatchlistEntry.GENRES))
                score= cursor.getDouble(cursor.getColumnIndex(DBContract.WatchlistEntry.SCORE))
                year = cursor.getString(cursor.getColumnIndex(DBContract.WatchlistEntry.YEAR_RELEASE))
                posterPath = cursor.getString(cursor.getColumnIndex(DBContract.WatchlistEntry.POSTER_PATH))

                movies.add(Movie(movieId, title, genres, score, year, posterPath))
                cursor.moveToNext()
            }
        }
        return movies
    }
}