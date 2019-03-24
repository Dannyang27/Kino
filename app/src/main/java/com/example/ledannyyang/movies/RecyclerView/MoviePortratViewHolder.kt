package com.example.ledannyyang.movies.RecyclerView

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.example.ledannyyang.movies.Model.MovieDetail.MovieDetail
import com.example.ledannyyang.movies.R

class MoviePortraitViewHolder(view : View) : RecyclerView.ViewHolder(view){
    var id = -1
    val portrait = view.findViewById(R.id.movie_portrait) as ImageView

    init {
        view.setOnClickListener {
            Toast.makeText(portrait.context, "Movie ID: $id", Toast.LENGTH_SHORT).show()
        }
    }
}
