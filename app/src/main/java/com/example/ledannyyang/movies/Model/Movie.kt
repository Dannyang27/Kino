package com.example.ledannyyang.movies.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(@PrimaryKey val id: Int,
                 val title: String,
                 val genres: String,
                 val score: Double?,
                 val releaseDate: String?,
                 val posterPath: String?)