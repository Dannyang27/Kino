package com.example.ledannyyang.movies.Retrofit

import com.example.ledannyyang.movies.Model.CastDetail.CastDetail
import com.example.ledannyyang.movies.Model.Credit.Credit
import com.example.ledannyyang.movies.Model.ExternalSocialNetwork.ExternalSocialNetwork
import com.example.ledannyyang.movies.Model.MovieDetail.MovieDetail
import com.example.ledannyyang.movies.Model.NowPlaying.NowPlaying
import com.example.ledannyyang.movies.Model.RecommendedMovie.RecommendedMovie
import com.example.ledannyyang.movies.Model.Review.Review
import com.example.ledannyyang.movies.Model.SimilarMovie.SimilarMovie
import com.example.ledannyyang.movies.Model.TopRated.TopRated
import com.example.ledannyyang.movies.Model.Upcoming.Upcoming
import com.example.ledannyyang.movies.Model.Video.Video
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    // returns detail given a movie ID
    @GET("/3/movie/{id}?api_key=6ee8506f55fda3da84e75f9a5f8baa76")
    fun getMovieDetail(@Path("id") movieID: String,
                       @Query("language") language: String) : Call<MovieDetail>

    // Gets current movies that are displayed at cinemas
    @GET("/3/movie/now_playing?api_key=6ee8506f55fda3da84e75f9a5f8baa76")
    fun getNowPlaying(@Query("language") language: String,
                      @Query("page") page : String,
                      @Query("region") region: String): Call<NowPlaying>

    // Gets top rated movies as per tmdb
    @GET( "/3/movie/top_rated?api_key=6ee8506f55fda3da84e75f9a5f8baa76")
    fun getTopRated(@Query("page") page : String,
                    @Query("language") language: String) : Call<TopRated>

    // Gets Upcoming Movies as per region
    @GET("/3/movie/upcoming?api_key=6ee8506f55fda3da84e75f9a5f8baa76")
    fun getUpcoming(@Query("language") language : String,
                    @Query("page") page : String) : Call<Upcoming>

    // Gets a list of movies that are similar to the id provided
    @GET("/3/movie/{id}/similar?api_key=6ee8506f55fda3da84e75f9a5f8baa76")
    fun getSimilarMoviesById(@Path("id") movieId : String,
                   @Query("language") language : String,
                   @Query("page") page: String) : Call<SimilarMovie>

    // Returns a list of recommended movies given a id
    @GET("/3/movie/{id}/recommendations?api_key=6ee8506f55fda3da84e75f9a5f8baa76")
    fun getRecommendedMoviesById(@Path("id") movieId : String,
                             @Query("language") language : String,
                             @Query("page") page: String) : Call<RecommendedMovie>

    // Returns a list of reviews written by randoms
    @GET("/3/movie/{id}/reviews?api_key=6ee8506f55fda3da84e75f9a5f8baa76")
    fun getReviewsById(@Path("id") movieId : String,
                       @Query("language") language : String,
                       @Query("page") page: String) : Call<Review>

    // returns a list of associated videos given a movie id
    @GET("/3/movie/{id}/videos?api_key=6ee8506f55fda3da84e75f9a5f8baa76")
    fun getVideosById(@Path("id") movieId : String,
                      @Query("language") language : String) : Call<Video>

    // returns a list of id of different social networks
    @GET("/3/movie/{id}/external_ids?api_key=6ee8506f55fda3da84e75f9a5f8baa76")
    fun getExternalSocialNetwork(@Path("id") movieId : String) : Call<ExternalSocialNetwork>

    // returns a list of the movie cast
    @GET("/3/movie/{id}/credits?api_key=6ee8506f55fda3da84e75f9a5f8baa76\n")
    fun getCredits(@Path("id") movieId: String) : Call<Credit>

    // returns info about the actor/actress
    @GET("/3/person/{id}?api_key=6ee8506f55fda3da84e75f9a5f8baa76")
    fun getCastDetail(@Path("id") castId: String,
                      @Query("language") language: String) : Call<CastDetail>
}