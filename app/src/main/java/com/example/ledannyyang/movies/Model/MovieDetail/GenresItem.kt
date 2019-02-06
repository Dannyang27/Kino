package com.example.ledannyyang.movies.Model.MovieDetail

import com.google.gson.annotations.SerializedName

data class GenresItem(@SerializedName("id")
                      val id: Int = 0,
                      @SerializedName("name")
                      val name: String = "")