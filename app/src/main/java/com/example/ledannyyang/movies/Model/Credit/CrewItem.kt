package com.example.ledannyyang.movies.Model.Credit

import com.google.gson.annotations.SerializedName

data class CrewItem(@SerializedName("credit_id")
                    val creditId: String = "",
                    @SerializedName("department")
                    val department: String = "",
                    @SerializedName("gender")
                    val gender: Int? = null,
                    @SerializedName("id")
                    val id: Int = 0,
                    @SerializedName("job")
                    val job: String = "",
                    @SerializedName("name")
                    val name: String = "",
                    @SerializedName("profile_path")
                    val profilePath: String? = null)