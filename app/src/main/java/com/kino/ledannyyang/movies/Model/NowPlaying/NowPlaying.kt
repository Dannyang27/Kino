package com.kino.ledannyyang.movies.Model.NowPlaying

import com.google.gson.annotations.SerializedName

data class NowPlaying(@SerializedName("results")
                          val results: List<NowPlayingItem>?,
                      @SerializedName("page")
                          val page: Int = 0,
                      @SerializedName("total_results")
                          val totalResults: Int = 0,
                      @SerializedName("dates")
                          val dates: Dates,
                      @SerializedName("total_pages")
                          val totalPages: Int = 0)