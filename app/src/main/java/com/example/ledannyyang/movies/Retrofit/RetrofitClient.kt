package com.example.ledannyyang.movies.Retrofit

import android.util.Log
import com.example.ledannyyang.movies.AllMightyDataController
import com.example.ledannyyang.movies.FragmentController.NowPlayingController
import com.example.ledannyyang.movies.FragmentMovieDetail.MovieDetailCastFragment
import com.example.ledannyyang.movies.FragmentMovieDetail.MovieDetailInfoFragment
import com.example.ledannyyang.movies.FragmentMovieDetail.MovieDetailReviewFragment
import com.example.ledannyyang.movies.Model.CastDetail.CastDetail
import com.example.ledannyyang.movies.Model.Credit.Credit
import com.example.ledannyyang.movies.Model.ExternalSocialNetwork.ExternalSocialNetwork
import com.example.ledannyyang.movies.Model.MovieDetail.MovieDetail
import com.example.ledannyyang.movies.Model.NowPlaying.NowPlaying
import com.example.ledannyyang.movies.Model.PortraitMovie.PortraitMovie
import com.example.ledannyyang.movies.Model.RecommendedMovie.RecommendedMovie
import com.example.ledannyyang.movies.Model.Review.Review
import com.example.ledannyyang.movies.Model.SimilarMovie.SimilarMovie
import com.example.ledannyyang.movies.Model.TopRated.TopRated
import com.example.ledannyyang.movies.Model.Upcoming.Upcoming
import com.example.ledannyyang.movies.Model.Video.Video
import com.example.ledannyyang.movies.Utils.StringUtils
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{

    val API = "APIQUERY"
    val baseUrl = "https://api.themoviedb.org"
    val api_key = "6ee8506f55fda3da84e75f9a5f8baa76"
    var nowplayingfetched = false

    val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service = retrofit.create(GithubService::class.java)

    fun getMovieDetail(id: Int, language:String = "en-US"){

        val call = service.getMovieDetail(id.toString(), language)

        call.enqueue(object : Callback<MovieDetail>{
            override fun onResponse(call: Call<MovieDetail>?, response: Response<MovieDetail>?) {
                val movieDetail = response?.body()?.copy()
                //Log.d(API, "Movie Detail Id = ${movieDetail?.id} Overview: ${movieDetail?.runtime}")

                movieDetail.let {
                    MovieDetailInfoFragment.setInfo(it!!)
                    AllMightyDataController.movieInfoMap.plus(Pair(id, it))
                }

            }
            override fun onFailure(call: Call<MovieDetail>?, t: Throwable?) {
                Log.d(API, "Could not get details movies")
            }
        })
    }

    fun getNowPlaying(language:String = "en-US", page: Int = 1, region: String){

        val call = service.getNowPlaying(language,page.toString(), region)

        call.enqueue(object : Callback<NowPlaying>{
            override fun onResponse(call: Call<NowPlaying>?, response: Response<NowPlaying>?) {
                nowplayingfetched = true
                response?.body()?.copy()?.results?.iterator()?.forEach {
                    NowPlayingController.nowPlayingItems.add(it)
                }
                NowPlayingController.viewAdapter.notifyDataSetChanged()
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
                topRatedResult = response.body()?.copy()
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
                upcomingResult = response.body()?.copy()
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

    fun getSimilarMoviesById( id: Int, language: String = "en-US", page : Int = 1){
        val call = service.getSimilarMoviesById( id.toString(), language, page.toString())

        call.enqueue(object : Callback<SimilarMovie>{
            override fun onResponse(call: Call<SimilarMovie>, response: Response<SimilarMovie>) {
                response.body()?.copy()?.results?.iterator()?.forEach {
                    MovieDetailInfoFragment.similarMovieItems.add(PortraitMovie(it.id, it.posterPath))
                }
                MovieDetailInfoFragment.similarMovieAdapter.notifyDataSetChanged()
                AllMightyDataController.movieInfoSimilarMap.plus(Pair(id, MovieDetailInfoFragment.similarMovieItems))
            }

            override fun onFailure(call: Call<SimilarMovie>, t: Throwable) {
                Log.d(API, "Could not get Similar Movies")
            }
        })
    }

    fun getRecommendedMoviesById( id: Int, language: String = "en-US", page : Int = 1){
        val call = service.getRecommendedMoviesById( id.toString(), language, page.toString())

        call.enqueue(object : Callback<RecommendedMovie>{

            override fun onResponse(call: Call<RecommendedMovie>, response: Response<RecommendedMovie>) {
                response.body()?.copy()?.results?.iterator()?.forEach {
                    MovieDetailInfoFragment.recommendedMovieItems.add(PortraitMovie(it.id, it.posterPath))
                }
                MovieDetailInfoFragment.recommendedMovieAdapter.notifyDataSetChanged()
                AllMightyDataController.movieInfoRecommendedMap.plus(Pair(id, MovieDetailInfoFragment.recommendedMovieItems))
            }

            override fun onFailure(call: Call<RecommendedMovie>, t: Throwable) {
                Log.d(API, "Could not get Recommended Movies")
            }
        })
    }

    fun getReviewsById( id: Int, language: String = "en-US", page : Int = 1){
        val call = service.getReviewsById( id.toString(), language, page.toString())

        call.enqueue(object : Callback<Review>{
            override fun onResponse(call: Call<Review>, response: Response<Review>) {
                response.body()?.copy()?.results?.iterator()?.forEach {
                    MovieDetailReviewFragment.reviews.add(it)
                }
                MovieDetailReviewFragment.reviewAdapter.notifyDataSetChanged()
                AllMightyDataController.movieReviewMap.plus(Pair(id, MovieDetailReviewFragment.reviews))
            }
            override fun onFailure(call: Call<Review>, t: Throwable) {
                Log.d(API, "Could not get Reviews from the Movie")
            }
        })
    }

    fun getVideosById( id: Int, language: String = "en-US") : Video?{
        val call = service.getVideosById( id.toString(), language)
        var videoResult : Video? = null

        call.enqueue(object : Callback<Video>{
            override fun onResponse(call: Call<Video>, response: Response<Video>) {
                videoResult = response.body()?.copy()
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
                socialNetwork = response.body()?.copy()

                Log.d(API, "Instagram = @${socialNetwork?.instagramId}, Twitter = @${socialNetwork?.twitterId}")

            }
            override fun onFailure(call: Call<ExternalSocialNetwork>, t: Throwable) {
                Log.d(API, "Could not get External Social Networks")
            }
        })

        return socialNetwork
    }

    fun getCredits( id: Int){
        val call = service.getCredits( id.toString())

        call.enqueue(object : Callback<Credit>{
            override fun onResponse(call: Call<Credit>, response: Response<Credit>) {
                response.body()?.copy()?.cast?.filter { !it.profilePath.isNullOrBlank()}?.sortedBy { it.order }?.take(10)?.iterator()?.forEach {
                    //Log.d(API, "Cast name ${it.name}, Character = ${it.id}, cast profile = ${it.profilePath}")
                    MovieDetailCastFragment.credits.add(it)
                }
                MovieDetailCastFragment.castAdapter.notifyDataSetChanged()
                AllMightyDataController.movieCastMap.plus(Pair(id, MovieDetailCastFragment.credits))
            }
            override fun onFailure(call: Call<Credit>, t: Throwable) {
                Log.d(API, "Could not get Credits")
            }
        })
    }


    fun getDirector( id: Int){
        val call = service.getCredits( id.toString())

        call.enqueue(object : Callback<Credit>{
            override fun onResponse(call: Call<Credit>, response: Response<Credit>) {
                val director = response.body()?.copy()?.crew?.filter { it.job.equals("Director") }?.map { it.name }
                director.let{
                    val director = StringUtils.removeBrackets(director!!)
                    MovieDetailInfoFragment.setDirector(director)
                    AllMightyDataController.movieInfoDirectorMap.put(id, director)
                }
            }
            override fun onFailure(call: Call<Credit>, t: Throwable) {
                Log.d(API, "Could not get Director")
            }
        })
    }

    fun getCastDetail( id: Int, language: String = "en-US") : CastDetail?{
        val call = service.getCastDetail( id.toString(), language)
        var castDetailResult : CastDetail? = null

        call.enqueue(object : Callback<CastDetail>{
            override fun onResponse(call: Call<CastDetail>, response: Response<CastDetail>) {
                castDetailResult = response.body()?.copy()
                Log.d(API, "Name= ${castDetailResult?.deathday}  Biography = ${castDetailResult?.biography}")
            }
            override fun onFailure(call: Call<CastDetail>, t: Throwable) {
                Log.d(API, "Could not get Cast Details")
            }
        })

        return castDetailResult
    }
}