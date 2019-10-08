package com.example.ledannyyang.movies.RecyclerView

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ledannyyang.movies.Activities.MovieDetailActivity
import com.example.ledannyyang.movies.AllMightyDataController
import com.example.ledannyyang.movies.DiffUtil.MovieDiffCallback
import com.example.ledannyyang.movies.Model.Movie
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.Room.MyRoomDatabase
import com.example.ledannyyang.movies.Utils.GenresUtils
import com.example.ledannyyang.movies.enums.MovieTypes
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivityAdapter(private val gridLayoutManager: GridLayoutManager? = null, private val movies: MutableList<Movie>, val fromFragment: MovieTypes) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(), CoroutineScope{

    private val job = Job()
    override val coroutineContext = Dispatchers.IO + job
    private lateinit var database: MyRoomDatabase

    class NowPlayingViewHolder( view : View) : RecyclerView.ViewHolder(view){
        var movieId = -1
        var releaseDate = ""
        val poster = view.findViewById(R.id.poster) as? ImageView
        val year = view.findViewById(R.id.release_date) as? TextView
        val title = view.findViewById(R.id.title) as? TextView
        val genre = view.findViewById(R.id.genre) as? TextView
        val vote = view.findViewById(R.id.vote_avg) as? TextView

        init{
            view.setOnClickListener {
                val intent = Intent(it.context, MovieDetailActivity::class.java)
                intent.putExtra(AllMightyDataController.currentMovieID, movieId)
                AllMightyDataController.releaseDate =  releaseDate
                it.context.startActivity(intent)
            }
        }
    }

    class GridViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var movieId = -1
        var releaseDate = ""
        val poster = view.findViewById(R.id.grid_image) as? ImageView
        val title = view.findViewById(R.id.grid_name) as? TextView

        init{
            view.setOnClickListener {
                val intent = Intent(it.context, MovieDetailActivity::class.java)
                intent.putExtra(AllMightyDataController.currentMovieID, movieId)
                AllMightyDataController.releaseDate =  releaseDate
                it.context.startActivity(intent)
            }
        }
    }

    enum class ViewType {
        LIST, GRID
    }

    override fun getItemViewType(position: Int): Int {
        return if(gridLayoutManager?.spanCount == 1){
            ViewType.LIST.ordinal
        }else{
            ViewType.GRID.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(viewType){
            ViewType.LIST.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.viewholder_movie_main, parent, false)

                return NowPlayingViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.viewholder_grid, parent, false)

                return GridViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie  = movies[position]
        val urlImage = AllMightyDataController.imageUrl.plus(movie.posterPath)

        if(holder is NowPlayingViewHolder){
            Picasso.get()
                .load(urlImage)
                .into(holder.poster)

            holder.movieId = movie.id
            holder.releaseDate = movie.releaseDate ?: ""
            holder.title?.text = movie.title
            holder.genre?.text = GenresUtils.getGenresFromString(movie.genres)


            if(fromFragment == MovieTypes.UPCOMING){
                holder.year?.visibility = View.GONE
                holder.vote?.text = movie.releaseDate
            }else{

                if(!movie.releaseDate.isNullOrEmpty()){
                    holder.year?.text = movie.releaseDate.substring(0, 4)
                }

                holder.vote?.text = if(movie.score != 0.0) movie.score.toString() else "N/A"
            }

        }else if( holder is GridViewHolder ){
            holder.movieId = movie.id
            holder.releaseDate = movie.releaseDate ?: ""

            Picasso.get()
                .load(urlImage)
                .into(holder.poster)

            val width = AllMightyDataController.screenWidth / 3

            holder.poster?.layoutParams?.width = width
            holder.poster?.layoutParams?.height = (width * 1.5).toInt()
            holder.title?.layoutParams?.width = width
            holder.title?.text = movie.title
        }

        database = MyRoomDatabase.getMyRoomDatabase(holder.itemView.context)!!
        if(fromFragment == MovieTypes.WATCHLIST){
            holder.itemView.setOnLongClickListener { view ->
                launch {
                    database.deleteMovie(movie)
                }.also {
                    Toast.makeText(view.context, "Removed from Watchlist", Toast.LENGTH_LONG).show()
                }

                true
            }
        }else{
            holder.itemView.setOnLongClickListener { view ->
                launch {
                    MyRoomDatabase.getMyRoomDatabase(view.context)?.addMovie(movie)
                }.also {
                    Toast.makeText(view.context, "Added to Watchlist", Toast.LENGTH_LONG).show()
                }
                true
            }
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