package com.example.ledannyyang.movies.Model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Movie(@PrimaryKey val id: Int,
                 val title: String,
                 val genres: String,
                 val score: Double,
                 val year: String,
                 val posterPath: String?)