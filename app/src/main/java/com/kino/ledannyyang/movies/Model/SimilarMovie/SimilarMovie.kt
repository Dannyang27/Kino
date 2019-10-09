package com.kino.ledannyyang.movies.Model.SimilarMovie

import com.google.gson.annotations.SerializedName

data class SimilarMovie(@SerializedName("page")
                        val page: Int = 0,
                        @SerializedName("results")
                        val results: List<SimilarMovieItem>?,
                        @SerializedName("total_pages")
                        val totalPages: Int = 0,
                        @SerializedName("total_results")
                        val totalResults: Int = 0)