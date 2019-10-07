package com.example.ledannyyang.movies.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie")
data class Movie(@PrimaryKey val id: Int = -1,
                 val title: String = "",
                 val genres: String = "",
                 val score: Double? = -1.0,
                 val releaseDate: String? = "",
                 val posterPath: String? = "")