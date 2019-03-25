package com.example.ledannyyang.movies.RecyclerView

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.ledannyyang.movies.R

class ReviewViewHolder(view : View) : RecyclerView.ViewHolder(view){
    val author = view.findViewById(R.id.movie_review_author) as TextView
    val content = view.findViewById(R.id.movie_review_content) as TextView
}