package com.example.ledannyyang.movies.RecyclerView

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.ledannyyang.movies.R

class CastViewHolder(view : View) : RecyclerView.ViewHolder(view){
    var id = -1
    val portrait = view.findViewById(R.id.cast_portrait) as ImageView
    val creditName = view.findViewById(R.id.movie_cast_name) as TextView
    val character = view.findViewById(R.id.movie_cast_character) as TextView

}