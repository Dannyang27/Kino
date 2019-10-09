package com.kino.ledannyyang.movies.Model.SimilarMovie

import com.google.gson.annotations.SerializedName

data class SimilarMovieItem(@SerializedName("id")
                       val id: Int = 0,
                            @SerializedName("video")
                       val video: Boolean = false,
                            @SerializedName("vote_count")
                       val voteCount: Int = 0,
                            @SerializedName("vote_average")
                       val voteAverage: Double = 0.0,
                            @SerializedName("title")
                       val title: String = "",
                            @SerializedName("release_date")
                       val releaseDate: String = "",
                            @SerializedName("original_language")
                       val originalLanguage: String = "",
                            @SerializedName("original_title")
                       val originalTitle: String = "",
                            @SerializedName("genre_ids")
                       val genreIds: List<Integer>?,
                            @SerializedName("backdrop_path")
                       val backdropPath: String? = null,
                            @SerializedName("adult")
                       val adult: Boolean = false,
                            @SerializedName("overview")
                       val overview: String = "",
                            @SerializedName("poster_path")
                       val posterPath: String? = null,
                            @SerializedName("popularity")
                       val popularity: Double = 0.0)