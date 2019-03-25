package com.example.ledannyyang.movies.FragmentMovieDetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ledannyyang.movies.Model.Review.ReviewItem
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.RecyclerView.MovieDetail.ReviewAdapter
import com.example.ledannyyang.movies.RecyclerView.NowPlaying.NowPlayingAdapter
import com.example.ledannyyang.movies.Retrofit.RetrofitClient

class MovieDetailReviewFragment : Fragment(){

    private lateinit var reviewRecyclerView : RecyclerView
    private lateinit var reviewViewManager : RecyclerView.LayoutManager

    companion object {
        lateinit var reviewAdapter: RecyclerView.Adapter<*>
        val reviews = mutableListOf<ReviewItem>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view =  inflater.inflate(R.layout.movie_detail_review, container, false)

        val movieId = activity?.intent?.getIntExtra( NowPlayingAdapter.NowPlayingViewHolder.ID, -1)


        reviewViewManager = LinearLayoutManager(activity)
        reviewAdapter = ReviewAdapter(reviews)

        reviewRecyclerView = view.findViewById<RecyclerView>(R.id.review_recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = reviewViewManager
            adapter = reviewAdapter
        }

        movieId.let {
            RetrofitClient.getReviewsById(movieId!!)
        }

        return view
    }
}