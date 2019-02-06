package com.example.ledannyyang.movies.Model.ExternalSocialNetwork

import com.google.gson.annotations.SerializedName

data class ExternalSocialNetwork(@SerializedName("id")
                                 val id: Int = 0,
                                 @SerializedName("imdb_id")
                                 val imdbId: String = "",
                                 @SerializedName("facebook_id")
                                 val facebookId: String = "",
                                 @SerializedName("instagram_id")
                                 val instagramId: String = "",
                                 @SerializedName("twitter_id")
                                 val twitterId: String = "")