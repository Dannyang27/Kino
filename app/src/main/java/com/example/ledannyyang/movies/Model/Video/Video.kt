package com.example.ledannyyang.movies.Model.Video

import com.google.gson.annotations.SerializedName

data class Video(@SerializedName("id")
                 val id: Int = 0,
                 @SerializedName("results")
                 val results: List<VideoItem>?)