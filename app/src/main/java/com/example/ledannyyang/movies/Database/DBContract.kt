package com.example.ledannyyang.movies.Database

import android.provider.BaseColumns

object DBContract {

    /* Inner class that defines the table contents */
    class WatchlistEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "watchlist"
            val ID = "id"
            val TITLE = "title"
            val GENRES = "genres"
            val SCORE = "score"
            val YEAR_RELEASE = "year_release"
        }
    }
}