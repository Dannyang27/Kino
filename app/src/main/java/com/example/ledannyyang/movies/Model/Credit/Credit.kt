package com.example.ledannyyang.movies.Model.Credit

import com.google.gson.annotations.SerializedName

data class Credit(@SerializedName("id")
                  val id: Int = 0,
                  @SerializedName("cast")
                  val cast: List<CastItem>?,
                  @SerializedName("crew")
                  val crew: List<CrewItem>?)