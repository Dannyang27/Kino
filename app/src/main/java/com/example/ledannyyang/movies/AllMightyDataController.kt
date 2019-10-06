package com.example.ledannyyang.movies

import com.example.ledannyyang.movies.Model.CastDetail.CastDetail
import com.example.ledannyyang.movies.Model.Credit.CastItem
import com.example.ledannyyang.movies.Model.MovieDetail.MovieDetail
import com.example.ledannyyang.movies.Model.PortraitMovie.PortraitMovie
import com.example.ledannyyang.movies.Model.Review.ReviewItem
import com.example.ledannyyang.movies.enums.MovieTypes

object AllMightyDataController{
    var nowPlayingPages = 1
    var upcomingMoviesPages = 1
    var releaseDate = ""

    val movieInfoMap = mapOf<Int, MovieDetail>()
    val castInfoMap = mapOf<Int, CastDetail>()
    val trailerLoaded = mapOf<Int, String>()
    val movieInfoRecommendedMap = mapOf<Int, List<PortraitMovie>>()
    val movieInfoSimilarMap =  mapOf<Int, List<PortraitMovie>>()
    val castFilmographyMap =  mapOf<Int, List<PortraitMovie>>()
    val movieInfoDirectorMap: MutableMap<Int, String> = mutableMapOf()
    val movieCastMap = mapOf<Int, MutableList<CastItem>>()
    val movieReviewMap = mapOf<Int, MutableList<ReviewItem>>()

    var movieDetailFrom = MovieTypes.NOWPLAYING

    const val currentMovieID = "MOVIE_ID"
    const val currentCastID = "CAST_ID"
    const val currentCreditID = "CREDIT_ID"
    const val imageUrl = "https://image.tmdb.org/t/p/w500/"
}