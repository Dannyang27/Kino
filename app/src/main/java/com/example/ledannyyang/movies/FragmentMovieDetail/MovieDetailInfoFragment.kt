package com.example.ledannyyang.movies.FragmentMovieDetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.ledannyyang.movies.AllMightyDataController
import com.example.ledannyyang.movies.Model.MovieDetail.MovieDetail
import com.example.ledannyyang.movies.Model.PortraitMovie.PortraitMovie
import com.example.ledannyyang.movies.Model.RecommendedMovie.RecommendedMovie
import com.example.ledannyyang.movies.Model.RecommendedMovie.RecommendedMovieItem
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.RecyclerView.MovieDetail.MoviePortraitAdapter
import com.example.ledannyyang.movies.RecyclerView.NowPlaying.NowPlayingAdapter
import com.example.ledannyyang.movies.Retrofit.RetrofitClient
import com.example.ledannyyang.movies.Utils.GenresUtils
import com.example.ledannyyang.movies.Utils.StringUtils
import com.squareup.picasso.Picasso

class MovieDetailInfoFragment : Fragment(){

    private lateinit var recommendedRecyclerView : RecyclerView
    private lateinit var similarRecyclerView : RecyclerView
    private lateinit var recommendedViewManager : RecyclerView.LayoutManager
    private lateinit var similarViewManager : RecyclerView.LayoutManager

    companion object {
        lateinit var portrait: ImageView
        lateinit var title: TextView
        lateinit var genre: TextView
        lateinit var duration: TextView
        lateinit var releasedDate: TextView
        lateinit var director: TextView
        lateinit var homepage: TextView
        lateinit var sinopse: TextView
        lateinit var userscore: TextView

        lateinit var recommendedMovieAdapter : RecyclerView.Adapter<*>
        var recommendedMovieItems = mutableListOf<PortraitMovie>()

        lateinit var similarMovieAdapter : RecyclerView.Adapter<*>
        var similarMovieItems = mutableListOf<PortraitMovie>()


        fun setInfo( movie : MovieDetail){

            val url = "https://image.tmdb.org/t/p/w500/${movie.posterPath}"
            Picasso.with(portrait.context)
                    .load(url)
                    .into(portrait)

            val genres = movie.genres?.map { it.name }

            title.text = movie.title
            genre.text = StringUtils.removeBrackets(genres!!)
            duration.text = "${movie.runtime} min"
            releasedDate.text = movie.releaseDate
            homepage.text = movie.homepage ?: "N/A"
            sinopse.text = movie.overview
            userscore.text =  userscore.text.toString().plus(movie.voteAverage)


        }

        fun setDirector( name: String){
            director.text = name
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view =  inflater.inflate(R.layout.movie_detail_info, container, false)
        val movieId = activity?.intent?.getIntExtra( AllMightyDataController.currentMovieID, -1)


        title = view.findViewById(R.id.movie_info_title_lbl)
        portrait = view.findViewById(R.id.movie_info_portrait)
        genre = view.findViewById(R.id.movie_info_genre_lbl)
        duration = view.findViewById(R.id.movie_info_duration_lbl)
        releasedDate = view.findViewById(R.id.movie_info_date_lbl)
        director = view.findViewById(R.id.movie_info_director_lbl)
        homepage = view.findViewById(R.id.movie_info_homepage_lbl)
        sinopse = view.findViewById(R.id.movie_info_sinopse)
        userscore = view.findViewById(R.id.movie_info_userscore)

        recommendedViewManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        similarViewManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        recommendedMovieAdapter = MoviePortraitAdapter(recommendedMovieItems)
        similarMovieAdapter = MoviePortraitAdapter(similarMovieItems)

        recommendedRecyclerView = view.findViewById<RecyclerView>(R.id.recommendedRv).apply{
            setHasFixedSize(true)
            layoutManager = recommendedViewManager
            adapter = recommendedMovieAdapter
        }

        similarRecyclerView = view.findViewById<RecyclerView>(R.id.similarRv).apply {
            setHasFixedSize(true)
            layoutManager = similarViewManager
            adapter = similarMovieAdapter
        }

        loadMovieDetailInfo(movieId)

        return view
    }

    private fun loadMovieDetailInfo( id : Int?){

        id.let {

            if(AllMightyDataController.movieInfoMap.containsKey(id)){
                setInfo(AllMightyDataController.movieInfoMap[id]!!)
            }else{
                RetrofitClient.getMovieDetail(id!!)
            }

            if(AllMightyDataController.movieInfoRecommendedMap.containsKey(id)){
                recommendedMovieItems = AllMightyDataController.movieInfoRecommendedMap[id]?.toMutableList()!!
                recommendedMovieAdapter.notifyDataSetChanged()
            }else{
                recommendedMovieItems.clear()
                RetrofitClient.getRecommendedMoviesById(id!!)
            }

            if(AllMightyDataController.movieInfoSimilarMap.containsKey(id)){
                similarMovieItems = AllMightyDataController.movieInfoSimilarMap[id]?.toMutableList()!!
                similarMovieAdapter.notifyDataSetChanged()
            }else{
                similarMovieItems.clear()
                RetrofitClient.getSimilarMoviesById(id!!)
            }

            if(AllMightyDataController.movieInfoDirectorMap.containsKey(id)){
                setDirector(AllMightyDataController.movieInfoDirectorMap[id]!!)
            }else{
                RetrofitClient.getDirector(id!!)
            }
        }
    }
}