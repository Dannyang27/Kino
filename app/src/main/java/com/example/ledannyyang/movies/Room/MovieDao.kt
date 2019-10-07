package com.example.ledannyyang.movies.Room

import androidx.room.*
import com.example.ledannyyang.movies.Model.Movie

@Dao
interface MovieDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Update
    fun update(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("SELECT * FROM Movie")
    fun getWatchlist() : MutableList<Movie>

    @Query("DELETE FROM Movie")
    fun deleteWatchlist()
}