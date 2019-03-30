package com.example.ledannyyang.movies.RecyclerView.Upcoming

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.ledannyyang.movies.Activities.MovieDetailActivity
import com.example.ledannyyang.movies.AllMightyDataController
import com.example.ledannyyang.movies.Model.Upcoming.UpcomingItem
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.Utils.GenresUtils
import com.squareup.picasso.Picasso

class UpcomingAdapter(private val movies: MutableList<UpcomingItem>):
        RecyclerView.Adapter<UpcomingAdapter.UpcomingViewHolder>(){

    inner class UpcomingViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var movieId = -1
        val poster = view.findViewById(R.id.upcoming_poster) as ImageView
        val releaseDate = view.findViewById(R.id.upcoming_release_date) as TextView
        val title = view.findViewById(R.id.upcoming_title) as TextView
        val genre = view.findViewById(R.id.upcoming_genre) as TextView
        val vote = view.findViewById(R.id.upcoming_vote_avg) as TextView

        init{
            view.setOnClickListener {
                Toast.makeText(it.context, "$movieId", Toast.LENGTH_LONG).show()
                val intent = Intent(it.context, MovieDetailActivity::class.java)
                intent.putExtra(AllMightyDataController.currentMovieID, movieId)
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): UpcomingViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.viewholder_upcoming, parent, false)

        return UpcomingViewHolder(view)
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        val movie  = movies[position]
        val url = "https://image.tmdb.org/t/p/w500/${movie.posterPath}"

        //TODO what happens if no portrait is provided
        Picasso.with(holder.poster.context)
                .load(url)
                .into(holder.poster)
        holder.movieId = movie.id
        holder.releaseDate.text = movie.releaseDate.substring(0, 4)
        holder.title.text = movie.title
        holder.genre.text = GenresUtils.getGenres(movies[position].genreIds)
        holder.vote.text = if(movie.voteAverage != 0.0) movie.voteAverage.toString() else "N/A"
    }

    override fun getItemCount() = movies.size
}