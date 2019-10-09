package com.kino.ledannyyang.movies.Model.CastDetail

import com.google.gson.annotations.SerializedName

data class CastDetail(@SerializedName("birthday")
                      val birthday: String? = "",
                      @SerializedName("known_for_department")
                      val knownForDepartment: String = "",
                      @SerializedName("deathday")
                      val deathday: String? = null,
                      @SerializedName("id")
                      val id: Int = 0,
                      @SerializedName("name")
                      val name: String = "",
                      @SerializedName("also_known_as")
                      val alsoKnownAs: List<String>? = null,
                      @SerializedName("gender")
                      val gender: Int = 0,
                      @SerializedName("biography")
                      val biography: String = "",
                      @SerializedName("popularity")
                      val popularity: Double = 0.0,
                      @SerializedName("place_of_birth")
                      val placeOfBirth: String? = null,
                      @SerializedName("profile_path")
                      val profilePath: String? = null,
                      @SerializedName("adult")
                      val adult: Boolean = false,
                      @SerializedName("imdb_id")
                      val imdbId: String = "",
                      @SerializedName("homepage")
                      val homepage: String? = null)