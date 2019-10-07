package com.example.ledannyyang.movies.Utils

object RegionUtils {
    fun getIso3166(region: String): String{
        var code = "GB"
        when(region){
            "France"-> code =  "FR"
            "Germany" -> code = "DE"
            "Italy" -> code = "IT"
            "United Kingdom" -> code = "GB"
            "United States of America" -> code = "US"
            "Spain" -> code = "ES"
        }

        return code
    }
}