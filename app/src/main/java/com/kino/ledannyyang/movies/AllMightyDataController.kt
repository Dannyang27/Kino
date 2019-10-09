package com.kino.ledannyyang.movies

import com.kino.ledannyyang.movies.Model.Credit.CastItem
import com.kino.ledannyyang.movies.Model.Movie
import com.kino.ledannyyang.movies.Model.MovieDetail.MovieDetail
import com.kino.ledannyyang.movies.Model.PortraitMovie.PortraitMovie
import com.kino.ledannyyang.movies.Model.Review.ReviewItem
import com.kino.ledannyyang.movies.enums.MovieTypes

object AllMightyDataController{
    var nowPlayingPages = 1
    var upcomingMoviesPages = 1
    var releaseDate = ""

    var screenWidth = -1

    val movieInfoMap = mapOf<Int, MovieDetail>()
    val trailerLoaded = mapOf<Int, String>()
    val movieInfoRecommendedMap = mapOf<Int, List<PortraitMovie>>()
    val movieInfoSimilarMap =  mapOf<Int, List<PortraitMovie>>()
    val movieInfoDirectorMap: MutableMap<Int, String> = mutableMapOf()
    val movieCastMap = mapOf<Int, MutableList<CastItem>>()
    val movieReviewMap = mapOf<Int, MutableList<ReviewItem>>()

    val topRatedMovies = mutableListOf<Movie>()

    var movieDetailFrom = MovieTypes.NOWPLAYING

    const val currentMovieID = "MOVIE_ID"
    const val imageUrl = "https://image.tmdb.org/t/p/w500/"
}