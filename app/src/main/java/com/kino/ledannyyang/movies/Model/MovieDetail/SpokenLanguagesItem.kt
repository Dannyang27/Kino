package com.kino.ledannyyang.movies.Model.MovieDetail

import com.google.gson.annotations.SerializedName

data class SpokenLanguagesItem(@SerializedName("iso_639_1")
                               val iso: String = "",
                               @SerializedName("name")
                               val name: String = "")