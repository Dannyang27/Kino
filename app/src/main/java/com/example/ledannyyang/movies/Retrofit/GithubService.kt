package com.example.ledannyyang.movies.Retrofit

import com.example.ledannyyang.movies.Model.Credit.Credit
import com.example.ledannyyang.movies.Model.MovieDetail.MovieDetail
import com.example.ledannyyang.movies.Model.NowPlaying.NowPlaying
import com.example.ledannyyang.movies.Model.RecommendedMovie.RecommendedMovie
import com.example.ledannyyang.movies.Model.Review.Review
import com.example.ledannyyang.movies.Model.SearchMovie.SearchMovie
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
    @GET("/3/movie/{id}")
    fun getMovieDetail(@Path("id") movieID: String,
                       @Query("api_key") api: String,
                       @Query("language") language: String) : Call<MovieDetail>


    @GET("/3/search/movie")
    fun getSearchMovie(@Query("api_key") api: String,
                       @Query("language") language: String,
                       @Query("query") query: String,
                       @Query("page") page: String) : Call<SearchMovie>

    // Gets current movies that are displayed at cinemas
    @GET("/3/movie/now_playing")
    fun getNowPlaying(@Query("api_key") api: String,
                      @Query("language") language: String,
                      @Query("page") page : String,
                      @Query("region") region: String): Call<NowPlaying>

    // Gets top rated movies as per tmdb
    @GET( "/3/movie/top_rated")
    fun getTopRated(@Query("api_key") api: String,
                    @Query("page") page : String,
                    @Query("language") language: String) : Call<TopRated>

    // Gets Upcoming Movies as per region
    @GET("/3/movie/upcoming")
    fun getUpcoming(@Query("api_key") api: String,
                    @Query("language") language : String,
                    @Query("page") page : String,
                    @Query("region") region: String) : Call<Upcoming>

    // Gets a list of movies that are similar to the id provided
    @GET("/3/movie/{id}/similar")
    fun getSimilarMoviesById(@Path("id") movieId : String,
                             @Query("api_key") api: String,
                             @Query("language") language : String,
                             @Query("page") page: String) : Call<SimilarMovie>

    // Returns a list of recommended movies given a id
    @GET("/3/movie/{id}/recommendations")
    fun getRecommendedMoviesById(@Path("id") movieId : String,
                                 @Query("api_key") api: String,
                                 @Query("language") language : String,
                                @Query("page") page: String) : Call<RecommendedMovie>

    // Returns a list of reviews written by randoms
    @GET("/3/movie/{id}/reviews")
    fun getReviewsById(@Path("id") movieId : String,
                       @Query("api_key") api: String,
                       @Query("language") language : String,
                       @Query("page") page: String) : Call<Review>

    // returns a list of associated videos given a movie id
    @GET("/3/movie/{id}/videos")
    fun getVideosById(@Path("id") movieId : String,
                      @Query("api_key") api: String,
                      @Query("language") language : String) : Call<Video>


    // returns a list of the movie cast
    @GET("/3/movie/{id}/credits")
    fun getCredits(@Path("id") movieId: String,
                   @Query("api_key") api: String) : Call<Credit>

}