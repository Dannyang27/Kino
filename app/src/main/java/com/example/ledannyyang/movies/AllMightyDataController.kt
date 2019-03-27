package com.example.ledannyyang.movies

import com.example.ledannyyang.movies.Model.Credit.CastItem
import com.example.ledannyyang.movies.Model.MovieDetail.MovieDetail
import com.example.ledannyyang.movies.Model.PortraitMovie.PortraitMovie
import com.example.ledannyyang.movies.Model.Review.ReviewItem

object AllMightyDataController{
    val movieInfoMap = mapOf<Int, MovieDetail>()
    val movieInfoRecommendedMap = mapOf<Int, List<PortraitMovie>>()
    val movieInfoSimilarMap =  mapOf<Int, List<PortraitMovie>>()
    val movieInfoDirectorMap: MutableMap<Int, String> = mutableMapOf()
    val movieCastMap = mapOf<Int, MutableList<CastItem>>()
    val movieReviewMap = mapOf<Int, MutableList<ReviewItem>>()
}