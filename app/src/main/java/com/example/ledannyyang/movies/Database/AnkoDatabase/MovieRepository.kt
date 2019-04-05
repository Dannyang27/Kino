package com.example.ledannyyang.movies.Database.AnkoDatabase

import android.content.Context
import android.util.Log
import com.example.ledannyyang.movies.FragmentController.WatchListController
import com.example.ledannyyang.movies.Model.Movie
import org.jetbrains.anko.db.*

class MovieRepository(val context: Context){

    fun findAll(): MutableList<Movie> = context.database.use {
        val movies = mutableListOf<Movie>()

        select(Movie.TABLE_NAME, Movie.MOVIE_ID, Movie.COLUMN_TITLE, Movie.COLUMN_GENRE,
                Movie.COLUMN_SCORE, Movie.COLUMN_YEAR, Movie.COLUMN_POSTERPATH)
                .parseList(object: MapRowParser<MutableList<Movie>>{
                    override fun parseRow(columns: Map<String, Any?>): MutableList<Movie> {
                        val id = columns.getValue(Movie.MOVIE_ID)
                        val title = columns.getValue(Movie.COLUMN_TITLE)
                        val genres = columns.getValue(Movie.COLUMN_GENRE)
                        val score = columns.getValue(Movie.COLUMN_SCORE)
                        val year = columns.getValue(Movie.COLUMN_YEAR)
                        val posterPath = columns.getValue(Movie.COLUMN_POSTERPATH)

                        val m = Movie(id.toString().toInt(), title.toString(), genres.toString(), score.toString().toDouble(), year.toString(), posterPath.toString())
                        //movies.add(m)
                        WatchListController.watchlistItems?.add(m)
                        WatchListController.viewAdapter.notifyDataSetChanged()
                        return movies
                    }
                })
        movies
    }

    fun create(movie: Movie) = context.database.use{
        insert(Movie.TABLE_NAME,
                Movie.MOVIE_ID to movie.id,
                Movie.COLUMN_TITLE to movie.title,
                Movie.COLUMN_GENRE to movie.genres,
                Movie.COLUMN_SCORE to movie.score,
                Movie.COLUMN_YEAR to movie.year,
                Movie.COLUMN_POSTERPATH to movie.posterPath)
    }

    fun update(movie: Movie) = context.database.use {
        val updateResult = update(Movie.TABLE_NAME,
                Movie.MOVIE_ID to movie.id,
                Movie.COLUMN_TITLE to movie.title,
                Movie.COLUMN_GENRE to movie.genres,
                Movie.COLUMN_SCORE to movie.score,
                Movie.COLUMN_YEAR to movie.year,
                Movie.COLUMN_POSTERPATH to movie.posterPath)
                .whereArgs("${Movie.MOVIE_ID} = {${movie.id}", Movie.MOVIE_ID to movie.id)
                .exec()

        Log.d("APIQUERY", "Update result code is $updateResult")
    }

    fun delete(id : String) = context.database.use {
//        delete(Movie.TABLE_NAME, whereClause = "title = How to Train Your Dragon: The Hidden World")
    }
}