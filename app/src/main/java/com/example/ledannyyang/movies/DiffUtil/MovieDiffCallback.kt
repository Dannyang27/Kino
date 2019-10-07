package com.example.ledannyyang.movies.DiffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.ledannyyang.movies.Model.Movie

class MovieDiffCallback(private val oldMovies: MutableList<Movie>, private val newMovies: MutableList<Movie>) : DiffUtil.Callback(){
    override fun getOldListSize() = oldMovies.size
    override fun getNewListSize() = newMovies.size
    override fun areItemsTheSame(oldPosition: Int, newPosition: Int) = oldMovies[oldPosition].id == newMovies[newPosition].id
    override fun areContentsTheSame(oldPosition: Int, newPosition: Int) = oldMovies[oldPosition] == newMovies[newPosition]
}