package com.example.ledannyyang.movies.Model.MovieDetail

import com.google.gson.annotations.SerializedName

data class ProductionCompaniesItem(@SerializedName("id")
                                   val id: Int = 0,
                                   @SerializedName("logo_path")
                                   val logoPath: String? = null,
                                   @SerializedName("name")
                                   val name: String = "",
                                   @SerializedName("origin_country")
                                   val originCountry: String = "")