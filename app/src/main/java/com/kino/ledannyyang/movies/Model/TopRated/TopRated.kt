package com.kino.ledannyyang.movies.Model.TopRated

import com.google.gson.annotations.SerializedName

data class TopRated(@SerializedName("page")
                    val page: Int = 0,
                    @SerializedName("total_results")
                    val totalResults: Int = 0,
                    @SerializedName("total_pages")
                    val totalPages: Int = 0,
                    @SerializedName("results")
                    val results: List<TopRatedItem>?)