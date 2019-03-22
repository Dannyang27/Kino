package com.example.ledannyyang.movies.RecyclerView.NowPlaying

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.ledannyyang.movies.Model.NowPlaying.NowPlayingItem
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.Utils.GenresUtils
import com.squareup.picasso.Picasso

class NowPlayingAdapter(private val movies: MutableList<NowPlayingItem>) :
        RecyclerView.Adapter<NowPlayingAdapter.NowPlayingViewHolder>(){

    class NowPlayingViewHolder( view : View) : RecyclerView.ViewHolder(view){
        val poster = view.findViewById(R.id.poster) as ImageView
        val releaseDate = view.findViewById(R.id.release_date) as TextView
        val title = view.findViewById(R.id.title) as TextView
        val genre = view.findViewById(R.id.genre) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingAdapter.NowPlayingViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.viewholder_nowplaying, parent, false)

        return NowPlayingViewHolder(view)
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        val url = "https://image.tmdb.org/t/p/w500/${movies[position].posterPath}"
        Picasso.with(holder.poster.context)
                .load(url)
                .into(holder.poster)

        holder.releaseDate.text= movies[position].releaseDate.substring(0, 4)
        holder.title.text= movies[position].title
        holder.genre.text= GenresUtils.getGenres(movies[position].genreIds)

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.releaseDate.context, "${movies[position].title}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = movies.size
}