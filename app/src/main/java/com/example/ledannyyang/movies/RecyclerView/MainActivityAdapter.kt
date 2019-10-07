package com.example.ledannyyang.movies.RecyclerView

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ledannyyang.movies.Activities.MovieDetailActivity
import com.example.ledannyyang.movies.AllMightyDataController
import com.example.ledannyyang.movies.DiffUtil.MovieDiffCallback
import com.example.ledannyyang.movies.Model.Movie
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.Room.MyRoomDatabase
import com.example.ledannyyang.movies.Utils.GenresUtils
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivityAdapter(private val movies: MutableList<Movie>, val isUpcoming: Boolean = false) :
        RecyclerView.Adapter<MainActivityAdapter.NowPlayingViewHolder>(), CoroutineScope{

    private val job = Job()
    override val coroutineContext = Dispatchers.IO + job

    class NowPlayingViewHolder( view : View) : RecyclerView.ViewHolder(view){
        var movieId = -1
        var releaseDate = ""
        val poster = view.findViewById(R.id.poster) as ImageView
        val year = view.findViewById(R.id.release_date) as TextView
        val title = view.findViewById(R.id.title) as TextView
        val genre = view.findViewById(R.id.genre) as TextView
        val vote = view.findViewById(R.id.vote_avg) as TextView

        init{
            view.setOnClickListener {
                val intent = Intent(it.context, MovieDetailActivity::class.java)
                intent.putExtra(AllMightyDataController.currentMovieID, movieId)
                AllMightyDataController.releaseDate =  releaseDate
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.viewholder_nowplaying, parent, false)

        return NowPlayingViewHolder(view)
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        val movie  = movies[position]
        val urlImage = AllMightyDataController.imageUrl.plus(movie.posterPath)
        Picasso.get()
                .load(urlImage)
                .into(holder.poster)

        holder.movieId = movie.id
        holder.releaseDate = movie.releaseDate ?: ""
        holder.title.text = movie.title
        holder.genre.text = GenresUtils.getGenresFromString(movie.genres)


        if(isUpcoming){
            holder.year.visibility = View.GONE
            holder.vote.text = movie.releaseDate
        }else{

            if(!movie.releaseDate.isNullOrEmpty()){
                holder.year.text = movie.releaseDate.substring(0, 4)
            }

            holder.vote.text = if(movie.score != 0.0) movie.score.toString() else "N/A"
        }

        holder.itemView.setOnLongClickListener { view ->
                launch {
                    MyRoomDatabase.getMyRoomDatabase(view.context)?.addMovie(movie)
                }.also {
                    Toast.makeText(view.context, "Added to Watchlist", Toast.LENGTH_LONG).show()
                }
                true
        }

        holder.poster.setOnLongClickListener {
            val viewGroup = holder.itemView.findViewById(android.R.id.content) as? ViewGroup
            val dialogView = LayoutInflater.from(it.context).inflate(R.layout.portrait_dialog, viewGroup, false)
            val portrait = dialogView.findViewById(R.id.dialog_image) as ImageView

            Picasso.get()
                    .load(urlImage)
                    .into(portrait)

            val alertBuilder = AlertDialog.Builder(it.context)
            alertBuilder.setView(dialogView)

            val alertDialog = alertBuilder.create()
            alertDialog.show()

            true
        }
    }

    override fun getItemCount(): Int = movies.size

    fun updateList( newMovies: MutableList<Movie>){
        val diffResult : DiffUtil.DiffResult = DiffUtil.calculateDiff(MovieDiffCallback(movies, newMovies))
        movies.clear()
        movies.addAll(newMovies)
        diffResult.dispatchUpdatesTo(this)
    }
}