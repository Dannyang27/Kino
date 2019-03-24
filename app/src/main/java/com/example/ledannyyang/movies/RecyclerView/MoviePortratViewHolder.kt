package com.example.ledannyyang.movies.RecyclerView

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.example.ledannyyang.movies.R

class MoviePortraitViewHolder(view : View) : RecyclerView.ViewHolder(view){
    val portrait = view.findViewById(R.id.movie_portrait) as ImageView
}
