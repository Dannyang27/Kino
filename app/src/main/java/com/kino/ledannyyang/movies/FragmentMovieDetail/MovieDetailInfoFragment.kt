package com.kino.ledannyyang.movies.FragmentMovieDetail

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kino.ledannyyang.movies.AllMightyDataController
import com.kino.ledannyyang.movies.Model.MovieDetail.MovieDetail
import com.kino.ledannyyang.movies.Model.PortraitMovie.PortraitMovie
import com.kino.ledannyyang.movies.R
import com.kino.ledannyyang.movies.RecyclerView.MovieDetail.MoviePortraitAdapter
import com.kino.ledannyyang.movies.Retrofit.RetrofitClient
import com.kino.ledannyyang.movies.Utils.StringUtils
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
        lateinit var trailer : Button

        lateinit var recommendedMovieLayout: LinearLayout
        lateinit var similarMovieLayout: LinearLayout

        lateinit var recommendedMovieAdapter : RecyclerView.Adapter<*>
        var recommendedMovieItems = mutableListOf<PortraitMovie>()

        lateinit var similarMovieAdapter : RecyclerView.Adapter<*>
        var similarMovieItems = mutableListOf<PortraitMovie>()

        var trailerKey: String? = null

        fun setRecommendedMovieLayout(){
            recommendedMovieLayout.visibility = View.VISIBLE
        }

        fun setSimilarMovieLayout(){
            similarMovieLayout.visibility = View.VISIBLE
        }


        fun setInfo( movie : MovieDetail){

            val url = "https://image.tmdb.org/t/p/w500/${movie.posterPath}"
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.nophoto)
                    .into(portrait)

            val genres = movie.genres?.map { it.name }

            title.text = movie.title
            genre.text = StringUtils.removeBrackets(genres!!)
            if(movie.runtime != null){
                duration.text = "${movie.runtime} min"
            }else{
                duration.text = "N/A"
            }

            releasedDate.text = AllMightyDataController.releaseDate
            homepage.text = movie.homepage ?: "N/A"
            sinopse.text = movie.overview
            userscore.text =  userscore.text.toString().plus(" ${movie.voteAverage}")
        }

        fun setDirector( name: String){
            director.text = name
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view =  inflater.inflate(R.layout.movie_detail_info, container, false)
        val movieId = activity?.intent?.getIntExtra( AllMightyDataController.currentMovieID, -1)

        recommendedMovieLayout = view.findViewById(R.id.recommendedmovies_layout)
        similarMovieLayout = view.findViewById(R.id.similarmovies_layout)

        title = view.findViewById(R.id.movie_info_title_lbl)
        portrait = view.findViewById(R.id.movie_info_portrait)
        genre = view.findViewById(R.id.movie_info_genre_lbl)
        duration = view.findViewById(R.id.movie_info_duration_lbl)
        releasedDate = view.findViewById(R.id.movie_info_date_lbl)
        director = view.findViewById(R.id.movie_info_director_lbl)
        homepage = view.findViewById(R.id.movie_info_homepage_lbl)
        sinopse = view.findViewById(R.id.movie_info_sinopse)
        userscore = view.findViewById(R.id.movie_info_userscore)
        trailer = view.findViewById(R.id.trailer_button)

        trailer.setOnClickListener { showTrailer() }

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
                if(recommendedMovieItems.isNotEmpty()){
                    setRecommendedMovieLayout()
                    recommendedMovieAdapter.notifyDataSetChanged()
                }
            }else{
                recommendedMovieItems.clear()
                RetrofitClient.getRecommendedMoviesById(id!!)
            }

            if(AllMightyDataController.movieInfoSimilarMap.containsKey(id)){
                similarMovieItems = AllMightyDataController.movieInfoSimilarMap[id]?.toMutableList()!!
                if(similarMovieItems.isNotEmpty()){
                    setSimilarMovieLayout()
                    similarMovieAdapter.notifyDataSetChanged()
                }
            }else{
                similarMovieItems.clear()
                RetrofitClient.getSimilarMoviesById(id!!)
            }

            if(AllMightyDataController.movieInfoDirectorMap.containsKey(id)){
                setDirector(AllMightyDataController.movieInfoDirectorMap[id]!!)
            }else{
                RetrofitClient.getDirector(id!!)
            }

            if(AllMightyDataController.trailerLoaded.containsKey(id)){
                trailerKey = AllMightyDataController.trailerLoaded[id]!!
            }else{
                trailerKey = null
                RetrofitClient.getVideosById(id!!)
            }
        }
    }

    private fun showTrailer(){
            if(trailerKey != null){
                val viewGroup = view?.findViewById(android.R.id.content) as? ViewGroup
                val dialogView = LayoutInflater.from(view?.context).inflate(R.layout.trailer_dialog, viewGroup, false)
                val trailerWebView = dialogView.findViewById(R.id.trailer_webview) as WebView

                trailerWebView.settings.javaScriptEnabled = true
                trailerWebView.settings.pluginState = WebSettings.PluginState.ON
                trailerWebView.loadUrl("https://www.youtube.com/embed/$trailerKey?autoplay=0&vq=small")
                trailerWebView.webChromeClient = WebChromeClient()

                val alertBuilder = AlertDialog.Builder(view?.context)
                alertBuilder.setView(dialogView)

                val alertDialog = alertBuilder.create()
                alertDialog.show()
            }else{
                Toast.makeText(activity?.applicationContext, "No trailer available", Toast.LENGTH_SHORT).show()
            }
    }
}