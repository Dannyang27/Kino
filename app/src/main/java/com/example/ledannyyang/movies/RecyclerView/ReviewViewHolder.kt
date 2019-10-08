package com.example.ledannyyang.movies.RecyclerView

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ledannyyang.movies.R

class ReviewViewHolder(view : View) : RecyclerView.ViewHolder(view){
    var isReviewOpened = false
    val author = view.findViewById(R.id.movie_review_author) as TextView
    val content = view.findViewById(R.id.movie_review_content) as TextView
    val viewMoreBtn = view.findViewById(R.id.movie_review_viewmore) as Button
}