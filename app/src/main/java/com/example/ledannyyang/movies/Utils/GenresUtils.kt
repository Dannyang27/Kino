package com.example.ledannyyang.movies.Utils


object GenresUtils{

    val genresMap = mapOf(28 to "Action", 12 to "Adventure", 16 to "Animation", 35 to "Comedy", 80 to "Crime",
            99 to "Documentary", 18 to "Drama", 10751 to "Family", 14 to "Fantasy", 36 to "History", 27 to "Horror", 10402 to "Music",
            9648 to "Mistery", 10749 to "Romance", 878 to "Science-fiction", 10770 to "TV Movie", 53 to "Thriller", 10752 to "War", 37 to "Western")

    fun getGenres(genreList: List<Int>) : String {
        var result = mutableListOf<String>()

        if(genreList.isEmpty()) {
            return "N/A"
        }

        for(n in genreList){
            if(genresMap.containsKey(n)){
                result.add(genresMap[n]!!)
            }
        }
        return result.joinToString{ it }
    }

    fun removeBrackets(genres : List<String>) : String{
        return genres.toString().substring(1, genres.toString().count()-1)
    }
}