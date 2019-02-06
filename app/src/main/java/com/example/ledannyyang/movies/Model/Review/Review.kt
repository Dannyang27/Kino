package com.example.ledannyyang.movies.Model.Review

import com.google.gson.annotations.SerializedName

data class Review(@SerializedName("id")
                  val id: Int = 0,
                  @SerializedName("page")
                  val page: Int = 0,
                  @SerializedName("results")
                  val results: List<ReviewItem>?,
                  @SerializedName("total_pages")
                  val totalPages: Int = 0,
                  @SerializedName("total_results")
                  val totalResults: Int = 0)