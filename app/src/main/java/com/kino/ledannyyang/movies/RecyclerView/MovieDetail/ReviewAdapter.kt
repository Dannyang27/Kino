package com.kino.ledannyyang.movies.RecyclerView.MovieDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kino.ledannyyang.movies.Model.Review.ReviewItem
import com.kino.ledannyyang.movies.R
import com.kino.ledannyyang.movies.RecyclerView.ReviewViewHolder

class ReviewAdapter(private val reviews: MutableList<ReviewItem>):
        RecyclerView.Adapter<ReviewViewHolder>(){

    private val DEFAULT_MAX_LINES = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.viewholder_review, parent, false)

        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.author.text = review.author
        holder.content.text = review.content

        holder.content.post {
            val lineCount = holder.content.lineCount
            if(lineCount < DEFAULT_MAX_LINES){
                holder.viewMoreBtn.visibility = View.GONE
            }else{
                holder.viewMoreBtn.setOnClickListener {
                    if(!holder.isReviewOpened){
                        holder.content.maxLines = Integer.MAX_VALUE
                        holder.viewMoreBtn.text = holder.author.context.getString(R.string.view_less)
                    }else{
                        holder.content.maxLines = DEFAULT_MAX_LINES
                        holder.viewMoreBtn.text = holder.author.context.getString(R.string.see_more)
                    }

                    holder.isReviewOpened = !holder.isReviewOpened
                }
            }
        }
    }

    override fun getItemCount(): Int = reviews.size
}