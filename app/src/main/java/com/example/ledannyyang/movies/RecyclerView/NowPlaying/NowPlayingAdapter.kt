package com.example.ledannyyang.movies.RecyclerView.NowPlaying

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.ledannyyang.movies.Activities.MovieDetailActivity
import com.example.ledannyyang.movies.Model.NowPlaying.NowPlayingItem
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.Utils.GenresUtils
import com.squareup.picasso.Picasso

class NowPlayingAdapter(private val movies: MutableList<NowPlayingItem>) :
        RecyclerView.Adapter<NowPlayingAdapter.NowPlayingViewHolder>(){

    class NowPlayingViewHolder( view : View) : RecyclerView.ViewHolder(view){
        var movieId = -1
        val poster = view.findViewById(R.id.poster) as ImageView
        val releaseDate = view.findViewById(R.id.release_date) as TextView
        val title = view.findViewById(R.id.title) as TextView
        val genre = view.findViewById(R.id.genre) as TextView
        val vote = view.findViewById(R.id.vote_avg) as TextView

        companion object {
            val ID = "MOVIE_ID"
        }

        init{
            view.setOnClickListener {
                Toast.makeText(it.context, "$movieId", Toast.LENGTH_LONG).show()
                val intent = Intent(it.context, MovieDetailActivity::class.java)
                intent.putExtra( ID, movieId )
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingAdapter.NowPlayingViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.viewholder_nowplaying, parent, false)

        return NowPlayingViewHolder(view)
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        val movie  = movies[position]
        val url = "https://image.tmdb.org/t/p/w500/${movie.posterPath}"
        Picasso.with(holder.poster.context)
                .load(url)
                .into(holder.poster)
        holder.movieId = movie.id
        holder.releaseDate.text = movie.releaseDate.substring(0, 4)
        holder.title.text = movie.title
        holder.genre.text = GenresUtils.getGenres(movies[position].genreIds)
        holder.vote.text = if(movie.voteAverage != 0.0) movie.voteAverage.toString() else "N/A"
    }

    override fun getItemCount(): Int = movies.size
}