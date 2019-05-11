package com.example.ledannyyang.movies.Database

import android.arch.persistence.room.*
import com.example.ledannyyang.movies.Model.Movie

@Dao
interface MovieDAO{
    @Query("SELECT * FROM Movie")
    fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun getMovieById(id: Int): Movie

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Update
    fun updateMovie(movie: Array<Movie>)

    @Delete
    fun deleteMovie(movie: Array<Movie>)
}