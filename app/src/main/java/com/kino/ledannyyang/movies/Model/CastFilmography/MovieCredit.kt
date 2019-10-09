package com.kino.ledannyyang.movies.Model.CastFilmography

import com.google.gson.annotations.SerializedName

data class MovieCredit(
        @SerializedName("credit_type")
        val creditType: String,
        @SerializedName("department")
        val department: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("job")
        val job: String,
        @SerializedName("media")
        val media: Media,
        @SerializedName("media_type")
        val mediaType: String,
        @SerializedName("person")
        val person: Person
)