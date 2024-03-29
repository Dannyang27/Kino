package com.kino.ledannyyang.movies.Model.MovieDetail

import com.google.gson.annotations.SerializedName

data class MovieDetail(@SerializedName("adult")
                       val adult: Boolean = false,
                       @SerializedName("backdrop_path")
                       val backdropPath: String? = null,
                       @SerializedName("belongs_to_collection")
                       val belongsToCollection: BelongsToCollection? = null,
                       @SerializedName("budget")
                       val budget: Int = 0,
                       @SerializedName("genres")
                       val genres: List<GenresItem>?,
                       @SerializedName("homepage")
                       val homepage: String? = null,
                       @SerializedName("id")
                       val id: Int = 0,
                       @SerializedName("imdb_id")
                       val imdbId: String? = null,
                       @SerializedName("original_language")
                       val originalLanguage: String = "",
                       @SerializedName("original_title")
                       val originalTitle: String = "",
                       @SerializedName("overview")
                       val overview: String? = null,
                       @SerializedName("popularity")
                       val popularity: Double = 0.0,
                       @SerializedName("poster_path")
                       val posterPath: String? = null,
                       @SerializedName("production_companies")
                       val productionCompanies: List<ProductionCompaniesItem>?,
                       @SerializedName("production_countries")
                       val productionCountries: List<ProductionCountriesItem>?,
                       @SerializedName("release_date")
                       val releaseDate: String = "",
                       @SerializedName("revenue")
                       val revenue: Int = 0,
                       @SerializedName("runtime")
                       val runtime: Int? = null,
                       @SerializedName("spoken_languages")
                       val spokenLanguages: List<SpokenLanguagesItem>?,
                       @SerializedName("status")
                       val status: String = "",
                       @SerializedName("tagline")
                       val tagline: String? = null,
                       @SerializedName("title")
                       val title: String = "",
                       @SerializedName("video")
                       val video: Boolean = false,
                       @SerializedName("vote_average")
                       val voteAverage: Double = 0.0,
                       @SerializedName("vote_count")
                       val voteCount: Int = 0)