package com.example.ledannyyang.movies.Model.Upcoming

import com.google.gson.annotations.SerializedName

data class Upcoming(@SerializedName("results")
                    val results: List<UpcomingItem>?,
                    @SerializedName("page")
                    val page: Int = 0,
                    @SerializedName("total_results")
                    val totalResults: Int = 0,
                    @SerializedName("dates")
                    val dates: Dates,
                    @SerializedName("total_pages")
                    val totalPages: Int = 0)