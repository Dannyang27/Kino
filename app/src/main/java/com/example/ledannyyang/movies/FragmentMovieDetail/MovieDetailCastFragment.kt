package com.example.ledannyyang.movies.FragmentMovieDetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ledannyyang.movies.Model.Credit.CastItem
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.RecyclerView.MovieDetail.CastAdapter
import com.example.ledannyyang.movies.RecyclerView.NowPlaying.NowPlayingAdapter
import com.example.ledannyyang.movies.Retrofit.RetrofitClient

class MovieDetailCastFragment : Fragment(){
    private lateinit var castRecyclerView : RecyclerView
    private lateinit var castViewManager : RecyclerView.LayoutManager

    companion object {
        lateinit var castAdapter : RecyclerView.Adapter<*>
        val credits = mutableListOf<CastItem>()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view =  inflater.inflate(R.layout.movie_detail_cast, container, false)

        val movieId = activity?.intent?.getIntExtra( NowPlayingAdapter.NowPlayingViewHolder.ID, -1)

        castViewManager = LinearLayoutManager(activity)
        castAdapter = CastAdapter(credits)

        castRecyclerView = view.findViewById<RecyclerView>(R.id.cast_recyclerview).apply{
            setHasFixedSize(true)
            layoutManager = castViewManager
            adapter = castAdapter
        }

        movieId.let { RetrofitClient.getCredits(movieId!!) }


        return view
    }
}