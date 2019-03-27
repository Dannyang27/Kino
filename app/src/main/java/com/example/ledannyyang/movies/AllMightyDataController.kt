package com.example.ledannyyang.movies

import com.example.ledannyyang.movies.Model.Credit.CastItem
import com.example.ledannyyang.movies.Model.MovieDetail.MovieDetail
import com.example.ledannyyang.movies.Model.PortraitMovie.PortraitMovie
import com.example.ledannyyang.movies.Model.Review.ReviewItem

object AllMightyDataController{
    val movieInfoMap: MutableMap<Int, MovieDetail> = mutableMapOf()
    val movieInfoRecommendedMap: MutableMap<Int, List<PortraitMovie>> = mutableMapOf()
    val movieInfoSimilarMap: MutableMap<Int, List<PortraitMovie>> = mutableMapOf()
    val movieInfoDirectorMap: MutableMap<Int, String> = mutableMapOf()
    val movieCastMap: MutableMap<Int, CastItem> = mutableMapOf()
    val movieReviewMap: MutableMap<Int, ReviewItem> = mutableMapOf()
}