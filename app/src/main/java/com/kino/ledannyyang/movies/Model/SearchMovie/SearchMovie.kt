package com.kino.ledannyyang.movies.Model.SearchMovie


import com.google.gson.annotations.SerializedName

data class SearchMovie(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val results: List<SeachMovieItem>?,
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
)