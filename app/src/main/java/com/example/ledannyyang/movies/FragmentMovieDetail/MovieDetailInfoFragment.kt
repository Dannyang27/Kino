package com.example.ledannyyang.movies.FragmentMovieDetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.ledannyyang.movies.Model.MovieDetail.MovieDetail
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.RecyclerView.NowPlaying.NowPlayingAdapter
import com.example.ledannyyang.movies.Retrofit.RetrofitClient
import com.example.ledannyyang.movies.Utils.GenresUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_detail_info.*

class MovieDetailInfoFragment : Fragment(){

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
        fun setInfo( movie : MovieDetail){

            val url = "https://image.tmdb.org/t/p/w500/${movie.posterPath}"
            Picasso.with(portrait.context)
                    .load(url)
                    .into(portrait)

            val genres = movie.genres?.map { it.name }

            title.text = movie.title
            genre.text = GenresUtils.removeBrackets(genres!!)
            duration.text = "${movie.runtime} min"
            releasedDate.text = movie.releaseDate
            director.text = "Unknown"
            homepage.text = movie.homepage
            sinopse.text = movie.overview
            userscore.text =  userscore.text.toString().plus(movie.voteAverage)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view =  inflater.inflate(R.layout.movie_detail_info, container, false)

        title = view.findViewById(R.id.movie_info_title_lbl)
        portrait = view.findViewById(R.id.movie_info_portrait)
        genre = view.findViewById(R.id.movie_info_genre_lbl)
        duration = view.findViewById(R.id.movie_info_duration_lbl)
        releasedDate = view.findViewById(R.id.movie_info_date_lbl)
        director = view.findViewById(R.id.movie_info_director_lbl)
        homepage = view.findViewById(R.id.movie_info_homepage_lbl)
        sinopse = view.findViewById(R.id.movie_info_sinopse)
        userscore = view.findViewById(R.id.movie_info_userscore)

        val movieId = activity?.intent?.getIntExtra( NowPlayingAdapter.NowPlayingViewHolder.ID, -1)
        if(movieId != null)
            RetrofitClient.getMovieDetail(movieId)

//        val releasedDate = movie_info_date_lbl
//        val director = movie_info_director_lbl
//        val homepage = movie_info_homepage_lbl




        return view
    }
}