package com.kino.ledannyyang.movies.RecyclerView.MovieDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kino.ledannyyang.movies.Model.PortraitMovie.PortraitMovie
import com.kino.ledannyyang.movies.R
import com.kino.ledannyyang.movies.RecyclerView.MoviePortraitViewHolder
import com.squareup.picasso.Picasso

class MoviePortraitAdapter(private val movies: MutableList<PortraitMovie>):
        RecyclerView.Adapter<MoviePortraitViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MoviePortraitViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.viewholder_movie_portrait, parent, false)

        return MoviePortraitViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviePortraitViewHolder, position: Int) {
        val movie = movies[position]
        holder.id = movie.id

        val url = "https://image.tmdb.org/t/p/w500/${movie.posterPath}"
        Picasso.get()
                .load(url)
                .into(holder.portrait)
    }

    override fun getItemCount(): Int = movies.size
}