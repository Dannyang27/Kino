package com.kino.ledannyyang.movies.Model.Video

import com.google.gson.annotations.SerializedName

data class VideoItem(@SerializedName("id")
                       val id: String = "",
                     @SerializedName("iso_639_1")
                       val iso_639: String = "",
                     @SerializedName("iso_3166_1")
                       val iso_3166: String = "",
                     @SerializedName("key")
                       val key: String = "",
                     @SerializedName("name")
                       val name: String = "",
                     @SerializedName("site")
                       val site: String = "",
                     @SerializedName("size")
                       val size: Int = 0,
                     @SerializedName("type")
                       val type: String = "")