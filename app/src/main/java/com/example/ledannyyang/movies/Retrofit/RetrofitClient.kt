package com.example.ledannyyang.movies.Retrofit

import android.util.Log
import com.example.ledannyyang.movies.Activities.TopRatedActivity
import com.example.ledannyyang.movies.AllMightyDataController
import com.example.ledannyyang.movies.FragmentController.NowPlayingController
import com.example.ledannyyang.movies.FragmentController.SearchController
import com.example.ledannyyang.movies.FragmentController.UpcomingController
import com.example.ledannyyang.movies.FragmentMovieDetail.MovieDetailCastFragment
import com.example.ledannyyang.movies.FragmentMovieDetail.MovieDetailInfoFragment
import com.example.ledannyyang.movies.FragmentMovieDetail.MovieDetailReviewFragment
import com.example.ledannyyang.movies.Model.Credit.Credit
import com.example.ledannyyang.movies.Model.ExternalSocialNetwork.ExternalSocialNetwork
import com.example.ledannyyang.movies.Model.Movie
import com.example.ledannyyang.movies.Model.MovieDetail.MovieDetail
import com.example.ledannyyang.movies.Model.NowPlaying.NowPlaying
import com.example.ledannyyang.movies.Model.PortraitMovie.PortraitMovie
import com.example.ledannyyang.movies.Model.RecommendedMovie.RecommendedMovie
import com.example.ledannyyang.movies.Model.Review.Review
import com.example.ledannyyang.movies.Model.SearchMovie.SearchMovie
import com.example.ledannyyang.movies.Model.SimilarMovie.SimilarMovie
import com.example.ledannyyang.movies.Model.TopRated.TopRated
import com.example.ledannyyang.movies.Model.Upcoming.Upcoming
import com.example.ledannyyang.movies.Model.Video.Video
import com.example.ledannyyang.movies.Utils.StringUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object RetrofitClient{

    val API = "APIQUERY"
    val baseUrl = "https://api.themoviedb.org"
    val api_key = "6ee8506f55fda3da84e75f9a5f8baa76"

    val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service = retrofit.create(GithubService::class.java)

    fun getMovieDetail(id: Int, language:String = "en-US") : Boolean {

        val call = service.getMovieDetail(id.toString(), language)
        var success = false
        call.enqueue(object : Callback<MovieDetail>{
            override fun onResponse(call: Call<MovieDetail>?, response: Response<MovieDetail>?) {
                val movieDetail = response?.body()?.copy()
                movieDetail.let {
                    MovieDetailInfoFragment.setInfo(it!!)
                    AllMightyDataController.movieInfoMap.plus(Pair(id, it))
                }
                success = true
            }
            override fun onFailure(call: Call<MovieDetail>?, t: Throwable?) {
                Log.d(API, "Could not get details movies")
                success = false
            }
        })
        return success
    }

    fun getSearchMovie(items: MutableList<Movie>, query: String, language:String = "en-US", page: Int = 1) : Boolean {

        val call = service.getSearchMovie(language, query, page.toString())
        var success = false

        call.enqueue(object: Callback<SearchMovie>{
            override fun onResponse(call: Call<SearchMovie>, response: Response<SearchMovie>) {
                val searchMovie = response.body()?.copy()
                val list = searchMovie?.results
                    ?.sortedByDescending { it.voteAverage }
                    ?.filterNot { it.posterPath.isNullOrEmpty() }

                if(list?.isEmpty()!!){
                    SearchController.setEmptyView()
                }else{
                    val movies = mutableListOf<Movie>()
                    movies.addAll(items)

                    list.forEach {
                        val movie = Movie(it.id, it.title, StringUtils.removeBrackets(it.genreIds.map { it.toString() }),
                            it.voteAverage, it.releaseDate, it.posterPath)
                        movies.add(movie)
                    }

                    SearchController.updateList(movies)
                    success = true
                }
            }

            override fun onFailure(call: Call<SearchMovie>, t: Throwable) {
                Log.d(API, "Could not get movies (Search)")
                success = false
            }
        })

        return success
    }

    fun getNowPlaying(items: MutableList<Movie>, language:String = "en-US", page: Int = 1, region: String = "GB") : Boolean{

        val call = service.getNowPlaying(language, page.toString(), region)
        var success = false
        call.enqueue(object : Callback<NowPlaying>{
            override fun onResponse(call: Call<NowPlaying>?, response: Response<NowPlaying>?) {
                val nowPlaying = response?.body()?.copy()
                AllMightyDataController.nowPlayingPages = nowPlaying?.totalPages!!
                val list = nowPlaying.results?.filterNot { it.posterPath.isNullOrEmpty() }

                val movies = mutableListOf<Movie>()
                movies.addAll(items)

                list?.forEach {
                    val movie = Movie(it.id, it.title, StringUtils.removeBrackets(it.genreIds.map { it.toString() }),
                                it.voteAverage, it.releaseDate, it.posterPath)
                    movies.add(movie)
                }

                NowPlayingController.updateList(movies)
                success = true
            }
            override fun onFailure(call: Call<NowPlaying>?, t: Throwable?) {
                Log.d(API, "Could not get Now Playing movies")
                success = false
            }
        })
        return success
    }


    fun getUpcoming(items: MutableList<Movie>, language: String = "en-US", page: Int = 1, region: String = "GB"): Boolean{
        val call = service.getUpcoming(language, page.toString(), region)
        var success = false
        call.enqueue(object : Callback<Upcoming>{
            override fun onResponse(call: Call<Upcoming>, response: Response<Upcoming>) {
                val upcomingMovie = response.body()?.copy()
                AllMightyDataController.upcomingMoviesPages = upcomingMovie?.totalPages!!
                val list = upcomingMovie.results
                            ?.filter { it.posterPath != ""}
                            ?.filter { LocalDate.now() < LocalDate.parse(it.releaseDate, DateTimeFormatter.ISO_DATE) }
                            ?.sortedBy { it.releaseDate }

                val movies = mutableListOf<Movie>()
                movies.addAll(items)

                list?.forEach {
                    val movie = Movie(it.id, it.title, StringUtils.removeBrackets(it.genreIds.map { it.toString() }),
                            it.voteAverage, it.releaseDate, it.posterPath)
                    movies.add(movie)
                }

                UpcomingController.updateList(movies)
                success = true
            }
            override fun onFailure(call: Call<Upcoming>, t: Throwable) {
                Log.d(API, "Could not get Upcoming Movies")
                success = false
            }
        })
        return success
    }

    fun getTopRated(language: String = "en-US", page: Int = 1): Boolean{

        val call = service.getTopRated(language, page.toString())
        var success = false

        call.enqueue(object: Callback<TopRated>{
            override fun onResponse(call: Call<TopRated>, response: Response<TopRated>) {
                val list = response.body()?.copy()?.results

                list?.filter { it.posterPath != "" }?.forEach {
                    val movie = Movie(it.id, it.title, StringUtils.removeBrackets(it.genreIds.map { it.toString() }),
                        it.voteAverage, it.releaseDate, it.posterPath)

                    AllMightyDataController.topRatedMovies.add(movie)
                }

                val movies = mutableListOf<Movie>()
                movies.addAll(AllMightyDataController.topRatedMovies)

                TopRatedActivity.updateList(movies)
                success = true
            }

            override fun onFailure(call: Call<TopRated>, t: Throwable) {
                Log.d(API, "Could not get top rated movies")
                success = false
            }
        })
        return success
    }

    fun getSimilarMoviesById( id: Int, language: String = "en-US", page : Int = 1): Boolean{
        val call = service.getSimilarMoviesById( id.toString(), language, page.toString())
        var success = false
        call.enqueue(object : Callback<SimilarMovie>{
            override fun onResponse(call: Call<SimilarMovie>, response: Response<SimilarMovie>) {
                val movies = response.body()?.copy()?.results
                if(movies?.isNotEmpty()!!){
                    MovieDetailInfoFragment.setSimilarMovieLayout()
                    movies.forEach {
                        MovieDetailInfoFragment.similarMovieItems.add(PortraitMovie(it.id, it.posterPath))
                    }
                    MovieDetailInfoFragment.similarMovieAdapter.notifyDataSetChanged()
                    AllMightyDataController.movieInfoSimilarMap.plus(Pair(id, MovieDetailInfoFragment.similarMovieItems))
                    success = true
                }
            }

            override fun onFailure(call: Call<SimilarMovie>, t: Throwable) {
                Log.d(API, "Could not get Similar Movies")
                success = false
            }
        })
        return success
    }

    fun getRecommendedMoviesById( id: Int, language: String = "en-US", page : Int = 1): Boolean{
        val call = service.getRecommendedMoviesById( id.toString(), language, page.toString())
        var success = false
        call.enqueue(object : Callback<RecommendedMovie>{

            override fun onResponse(call: Call<RecommendedMovie>, response: Response<RecommendedMovie>) {
                val movies = response.body()?.copy()?.results
                if(movies?.isNotEmpty()!!){
                    MovieDetailInfoFragment.setRecommendedMovieLayout()
                    movies.forEach {
                        MovieDetailInfoFragment.recommendedMovieItems.add(PortraitMovie(it.id, it.posterPath))
                    }
                    MovieDetailInfoFragment.recommendedMovieAdapter.notifyDataSetChanged()
                    AllMightyDataController.movieInfoRecommendedMap.plus(Pair(id, MovieDetailInfoFragment.recommendedMovieItems))
                    success = true
                }
            }

            override fun onFailure(call: Call<RecommendedMovie>, t: Throwable) {
                Log.d(API, "Could not get Recommended Movies")
                success = false
            }
        })
        return success
    }

    fun getReviewsById( id: Int, language: String = "en-US", page : Int = 1) : Boolean {
        val call = service.getReviewsById( id.toString(), language, page.toString())
        var success = false
        call.enqueue(object : Callback<Review>{
            override fun onResponse(call: Call<Review>, response: Response<Review>) {
                val reviews = response.body()?.copy()?.results

                if(reviews?.isEmpty()!!){
                    MovieDetailReviewFragment.setEmptyView()
                }else{
                    reviews.forEach {
                        MovieDetailReviewFragment.reviews.add(it)
                    }

                    MovieDetailReviewFragment.reviewAdapter.notifyDataSetChanged()
                    AllMightyDataController.movieReviewMap.plus(Pair(id, MovieDetailReviewFragment.reviews))
                }

                success = true
            }
            override fun onFailure(call: Call<Review>, t: Throwable) {
                Log.d(API, "Could not get Reviews from the Movie")
                success = false
            }
        })
        return success
    }

    // gets only first trailer if exist
    fun getVideosById( id: Int, language: String = "en-US"): Boolean{
        val call = service.getVideosById( id.toString(), language)
        var success = false
        call.enqueue(object : Callback<Video>{
            override fun onResponse(call: Call<Video>, response: Response<Video>) {
                val videoItem = response.body()?.copy()?.results
                if(videoItem?.getOrNull(0) != null){
                    val key = videoItem?.get(0).key
                    MovieDetailInfoFragment.trailerKey = key
                    AllMightyDataController.trailerLoaded.plus(Pair(id, key))
                    success = true
                }else{
                    success = false
                }
            }
            override fun onFailure(call: Call<Video>, t: Throwable) {
                Log.d(API, "Could not get trailers from the Movie")
                success = false
            }
        })
        return success
    }

    fun getExternalSocialNetwork( id: Int) : Boolean{
        val call = service.getExternalSocialNetwork( id.toString())
        var success = false
        call.enqueue(object : Callback<ExternalSocialNetwork>{
            override fun onResponse(call: Call<ExternalSocialNetwork>, response: Response<ExternalSocialNetwork>) {
                val socialNetwork = response.body()?.copy()
                Log.d(API, "Instagram = @${socialNetwork?.instagramId}, Twitter = @${socialNetwork?.twitterId}")
                success = true
            }
            override fun onFailure(call: Call<ExternalSocialNetwork>, t: Throwable) {
                Log.d(API, "Could not get External Social Networks")
                success = false
            }
        })
        return success
    }

    fun getCredits( id: Int): Boolean{
        val call = service.getCredits( id.toString())
        var success = false
        call.enqueue(object : Callback<Credit>{
            override fun onResponse(call: Call<Credit>, response: Response<Credit>) {
                response.body()?.copy()?.cast?.filter { !it.profilePath.isNullOrBlank()}?.sortedBy { it.order }?.take(10)?.iterator()?.forEach {
                    //Log.d(API, "Cast name ${it.name}, Character = ${it.id}, cast profile = ${it.profilePath}")
                    MovieDetailCastFragment.credits.add(it)
                }
                MovieDetailCastFragment.castAdapter.notifyDataSetChanged()
                AllMightyDataController.movieCastMap.plus(Pair(id, MovieDetailCastFragment.credits))
                success = true
            }
            override fun onFailure(call: Call<Credit>, t: Throwable) {
                Log.d(API, "Could not get Credits")
                success = false
            }
        })
        return success
    }


    fun getDirector( id: Int) : Boolean{
        val call = service.getCredits( id.toString())
        var success = false
        call.enqueue(object : Callback<Credit>{
            override fun onResponse(call: Call<Credit>, response: Response<Credit>) {
                val director = response.body()?.copy()?.crew?.filter { it.job.equals("Director") }?.map { it.name }
                director.let{
                    val director = StringUtils.removeBrackets(director!!)
                    MovieDetailInfoFragment.setDirector(director)
                    AllMightyDataController.movieInfoDirectorMap.put(id, director)
                }
                success = true
            }
            override fun onFailure(call: Call<Credit>, t: Throwable) {
                Log.d(API, "Could not get Director")
                success = false
            }
        })
        return success
    }
}