package com.example.ledannyyang.movies.RecyclerView.MovieDetail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ledannyyang.movies.Model.Review.ReviewItem
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.RecyclerView.ReviewViewHolder

class ReviewAdapter(private val reviews: MutableList<ReviewItem>):
        RecyclerView.Adapter<ReviewViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.viewholder_review, parent, false)

        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.author.text = review.author
        holder.content.text = review.content
    }

    override fun getItemCount(): Int = reviews.size
}