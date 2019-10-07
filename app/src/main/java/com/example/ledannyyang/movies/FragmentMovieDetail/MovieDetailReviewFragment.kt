package com.example.ledannyyang.movies.FragmentMovieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ledannyyang.movies.AllMightyDataController
import com.example.ledannyyang.movies.Model.Review.ReviewItem
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.RecyclerView.HorizontalDivider
import com.example.ledannyyang.movies.RecyclerView.MovieDetail.ReviewAdapter
import com.example.ledannyyang.movies.Retrofit.RetrofitClient

class MovieDetailReviewFragment : Fragment(){

    private lateinit var reviewRecyclerView : RecyclerView
    private lateinit var reviewViewManager : RecyclerView.LayoutManager


    companion object {
        lateinit var reviewAdapter: RecyclerView.Adapter<*>
        lateinit var layout: LinearLayout
        var reviews = mutableListOf<ReviewItem>()

        fun setEmptyView(){
            layout.visibility = View.VISIBLE
        }

        fun hideEmptyView(){
            layout.visibility = View.GONE
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view =  inflater.inflate(R.layout.movie_detail_review, container, false)
        layout = view.findViewById(R.id.review_layout)

        val movieId = activity?.intent?.getIntExtra( AllMightyDataController.currentMovieID, -1)

        reviewViewManager = LinearLayoutManager(activity)
        reviewAdapter = ReviewAdapter(reviews)

        reviewRecyclerView = view.findViewById<RecyclerView>(R.id.review_recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = reviewViewManager
            addItemDecoration(HorizontalDivider(this.context))
            adapter = reviewAdapter
        }

        movieId.let {
            if(AllMightyDataController.movieReviewMap.containsKey(movieId)){
                reviews = AllMightyDataController.movieReviewMap[movieId]!!
                reviewAdapter.notifyDataSetChanged()
            }else{
                reviews.clear()
                RetrofitClient.getReviewsById(movieId!!)
            }
        }

        return view
    }
}