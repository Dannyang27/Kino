package com.example.ledannyyang.movies.Model

data class Movie(val id: Int, val title: String, val genres: String, val score: Double, val year: String, val posterPath: String?){
    companion object {
        val TABLE_NAME = "WatchlistMovie"
        val MOVIE_ID = "id"
        val COLUMN_TITLE = "title"
        val COLUMN_GENRE = "genres"
        val COLUMN_SCORE = "score"
        val COLUMN_YEAR = "year"
        val COLUMN_POSTERPATH = "poster_path"
    }
}