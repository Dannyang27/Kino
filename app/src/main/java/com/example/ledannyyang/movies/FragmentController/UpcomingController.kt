package com.example.ledannyyang.movies.FragmentController

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.ledannyyang.movies.AllMightyDataController
import com.example.ledannyyang.movies.Model.Movie
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.RecyclerView.HorizontalDivider
import com.example.ledannyyang.movies.RecyclerView.MainActivityAdapter
import com.example.ledannyyang.movies.Retrofit.RetrofitClient

class UpcomingController : Fragment(){

    private lateinit var recyclerView : RecyclerView
    private lateinit var viewManager : RecyclerView.LayoutManager

    companion object {
        lateinit var viewAdapter : RecyclerView.Adapter<*>
        var upcomingItems = mutableListOf<Movie>()
        var page = 1
        fun newInstance(): UpcomingController = UpcomingController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_upcoming, container, false)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = MainActivityAdapter(upcomingItems)

        if(!RetrofitClient.upcomingfetched)
            RetrofitClient.getUpcoming(page = page, region = "ES")

        recyclerView = view.findViewById<RecyclerView>(R.id.upcoming_recyclerview).apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            addItemDecoration(HorizontalDivider(this.context))
            adapter = viewAdapter

            addOnScrollListener(object: RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val isBottomReached = !recyclerView.canScrollVertically(1)
                    if (isBottomReached && AllMightyDataController.upcomingMoviesPages > page){
                        RetrofitClient.getUpcoming(page = page, region = "ES")
                    }
                }
            })
        }

        return view
    }


}