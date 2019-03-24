package com.example.ledannyyang.movies.Utils

object StringUtils{
    fun removeBrackets(genres : List<String>) : String{
        return genres.toString().substring(1, genres.toString().count()-1)
    }
}