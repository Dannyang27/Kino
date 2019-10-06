package com.example.ledannyyang.movies.RecyclerView

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ledannyyang.movies.R

class CastViewHolder(view : View) : RecyclerView.ViewHolder(view){
    var id = -1
    var creditId = ""
    val portrait = view.findViewById(R.id.cast_portrait) as ImageView
    val creditName = view.findViewById(R.id.movie_cast_name) as TextView
    val character = view.findViewById(R.id.movie_cast_character) as TextView
}
