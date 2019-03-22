package com.example.ledannyyang.movies.Retrofit

import android.util.Log
import com.example.ledannyyang.movies.FragmentController.NowPlayingController
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
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{

    val API = "APIQUERY"
    val baseUrl = "https://api.themoviedb.org"
    val api_key = "6ee8506f55fda3da84e75f9a5f8baa76"

    val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service = retrofit.create(GithubService::class.java)

    fun getMovieDetail(id: Int, language:String = "en-US") : MovieDetail? {

        val call = service.getMovieDetail(id.toString(), language)
        var movieDetailResult : MovieDetail? = null

        call.enqueue(object : Callback<MovieDetail>{
            override fun onResponse(call: Call<MovieDetail>?, response: Response<MovieDetail>?) {
                movieDetailResult = response?.body()?.copy()
                Log.d(API, "Movie Title = ${movieDetailResult?.title}")
                movieDetailResult?.genres?.iterator()?.forEach {
                    Log.d(API, it.name)
                }
                Log.d(API, "Movie Overview = ${movieDetailResult?.overview}")

            }
            override fun onFailure(call: Call<MovieDetail>?, t: Throwable?) {
                Log.d(API, "Could not get details movies")
            }
        })

        return movieDetailResult
    }

    fun getNowPlaying(language:String = "en-US", page: Int = 1, region: String){

        val call = service.getNowPlaying(language,page.toString(), region)

        call.enqueue(object : Callback<NowPlaying>{
            override fun onResponse(call: Call<NowPlaying>?, response: Response<NowPlaying>?) {
                response?.body()?.copy()?.results?.iterator()?.forEach {
                    Log.d(API, "Movie Id = ${it.id}, title = ${it.originalTitle}, vote_avg = ${it.voteAverage}\n")
                    NowPlayingController.nowPlayingItems?.add(it)
                    NowPlayingController.viewAdapter?.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<NowPlaying>?, t: Throwable?) {
                Log.d(API, "Could not get Now Playing movies")
            }
        })
    }

    fun getTopRated(language: String = "en-US", page: Int = 1) : TopRated?{

        val call = service.getTopRated(language, page.toString())
        var topRatedResult : TopRated? = null

        call.enqueue(object: Callback<TopRated>{
            override fun onResponse(call: Call<TopRated>, response: Response<TopRated>) {
                topRatedResult = response?.body()?.copy()
                topRatedResult?.results?.iterator()?.forEach {
                    Log.d(API, "TopRated movie id ${it.id}, title = ${it.title}")
                }
            }

            override fun onFailure(call: Call<TopRated>, t: Throwable) {
                Log.d(API, "Could not get top rated movies")
            }
        })

        return topRatedResult
    }

    fun getUpcoming(language: String = "en-US", page: Int = 1) : Upcoming?{
        val call = service.getUpcoming(language, page.toString())
        var upcomingResult : Upcoming? = null

        call.enqueue(object : Callback<Upcoming>{
            override fun onResponse(call: Call<Upcoming>, response: Response<Upcoming>) {
                upcomingResult = response?.body()?.copy()
                upcomingResult?.results?.iterator()?.forEach {
                    Log.d(API, "Upcoming movie id ${it.id}, title = ${it.title}")
                }
            }
            override fun onFailure(call: Call<Upcoming>, t: Throwable) {
                Log.d(API, "Could not get Upcoming Movies")
            }
        })

        return upcomingResult
    }

    fun getSimilarMoviesById( id: Int, language: String = "en-US", page : Int = 1) : SimilarMovie?{
        val call = service.getSimilarMoviesById( id.toString(), language, page.toString())
        var similarMovieResult : SimilarMovie? = null

        call.enqueue(object : Callback<SimilarMovie>{
            override fun onResponse(call: Call<SimilarMovie>, response: Response<SimilarMovie>) {
                similarMovieResult = response?.body()?.copy()
                similarMovieResult?.results?.iterator()?.forEach {
                    Log.d(API, "Similar movie id ${it.id}, title = ${it.title}")
                }
            }

            override fun onFailure(call: Call<SimilarMovie>, t: Throwable) {
                Log.d(API, "Could not get Similar Movies")
            }
        })

        return similarMovieResult
    }

    fun getRecommendedMoviesById( id: Int, language: String = "en-US", page : Int = 1) : RecommendedMovie?{
        val call = service.getRecommendedMoviesById( id.toString(), language, page.toString())
        var recommendedMovieResult : RecommendedMovie? = null

        call.enqueue(object : Callback<RecommendedMovie>{
            override fun onFailure(call: Call<RecommendedMovie>, t: Throwable) {
                Log.d(API, "Could not get Recommended Movies")
            }

            override fun onResponse(call: Call<RecommendedMovie>, response: Response<RecommendedMovie>) {
                recommendedMovieResult = response?.body()?.copy()
                recommendedMovieResult?.results?.iterator()?.forEach {
                    Log.d(API, "Recommended movie id ${it.id}, title = ${it.title}")
                }
            }
        })

        return recommendedMovieResult
    }

    fun getReviewsById( id: Int, language: String = "en-US", page : Int = 1) : Review?{
        val call = service.getReviewsById( id.toString(), language, page.toString())
        var reviewsResult : Review? = null

        call.enqueue(object : Callback<Review>{
            override fun onResponse(call: Call<Review>, response: Response<Review>) {
                reviewsResult = response?.body()?.copy()
                reviewsResult?.results?.iterator()?.forEach {
                    Log.d(API, "Review by ${it.author}, content = ${it.content}")
                }
            }
            override fun onFailure(call: Call<Review>, t: Throwable) {
                Log.d(API, "Could not get Reviews from the Movie")
            }
        })

        return reviewsResult
    }

    fun getVideosById( id: Int, language: String = "en-US") : Video?{
        val call = service.getVideosById( id.toString(), language)
        var videoResult : Video? = null

        call.enqueue(object : Callback<Video>{
            override fun onResponse(call: Call<Video>, response: Response<Video>) {
                videoResult = response?.body()?.copy()
                videoResult?.results?.iterator()?.forEach {
                    Log.d(API, "name by ${it.name}, key = ${it.key}")
                }
            }
            override fun onFailure(call: Call<Video>, t: Throwable) {
                Log.d(API, "Could not get trailers from the Movie")
            }
        })

        return videoResult
    }

    fun getExternalSocialNetwork( id: Int) : ExternalSocialNetwork?{
        val call = service.getExternalSocialNetwork( id.toString())
        var socialNetwork : ExternalSocialNetwork? = null

        call.enqueue(object : Callback<ExternalSocialNetwork>{
            override fun onResponse(call: Call<ExternalSocialNetwork>, response: Response<ExternalSocialNetwork>) {
                socialNetwork = response?.body()?.copy()

                Log.d(API, "Instagram = @${socialNetwork?.instagramId}, Twitter = @${socialNetwork?.twitterId}")

            }
            override fun onFailure(call: Call<ExternalSocialNetwork>, t: Throwable) {
                Log.d(API, "Could not get External Social Networks")
            }
        })

        return socialNetwork
    }

    fun getCredits( id: Int) : Credit?{
        val call = service.getCredits( id.toString())
        var creditResult : Credit? = null

        call.enqueue(object : Callback<Credit>{
            override fun onResponse(call: Call<Credit>, response: Response<Credit>) {
                creditResult = response?.body()?.copy()
                creditResult?.cast?.iterator()?.forEach {
                    Log.d(API, "Cast name ${it.castId}, Character = ${it.id}, cast id = ${it.creditId}")
                }
            }
            override fun onFailure(call: Call<Credit>, t: Throwable) {
                Log.d(API, "Could not get Credits")
            }
        })

        return creditResult
    }

    fun getCastDetail( id: Int, language: String = "en-US") : CastDetail?{
        val call = service.getCastDetail( id.toString(), language)
        var castDetailResult : CastDetail? = null

        call.enqueue(object : Callback<CastDetail>{
            override fun onResponse(call: Call<CastDetail>, response: Response<CastDetail>) {
                castDetailResult = response?.body()?.copy()
                Log.d(API, "Name= ${castDetailResult?.deathday}  Biography = ${castDetailResult?.biography}")
            }
            override fun onFailure(call: Call<CastDetail>, t: Throwable) {
                Log.d(API, "Could not get Cast Details")
            }
        })

        return castDetailResult
    }
}