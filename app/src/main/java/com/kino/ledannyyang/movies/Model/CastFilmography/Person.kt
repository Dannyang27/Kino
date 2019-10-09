package com.kino.ledannyyang.movies.Model.CastFilmography

import com.google.gson.annotations.SerializedName

data class Person(
        @SerializedName("adult")
        val adult: Boolean,
        @SerializedName("gender")
        val gender: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("known_for")
        val castFilmography: List<CastFilmography>,
        @SerializedName("known_for_department")
        val knownForDepartment: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("popularity")
        val popularity: Double,
        @SerializedName("profile_path")
        val profilePath: String
)