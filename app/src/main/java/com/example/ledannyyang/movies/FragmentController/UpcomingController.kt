package com.example.ledannyyang.movies.FragmentController

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ledannyyang.movies.Model.Upcoming.UpcomingItem
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.RecyclerView.HorizontalDivider
import com.example.ledannyyang.movies.RecyclerView.Upcoming.UpcomingAdapter
import com.example.ledannyyang.movies.Retrofit.RetrofitClient

class UpcomingController : Fragment(){

    private lateinit var recyclerView : RecyclerView
    private lateinit var viewManager : RecyclerView.LayoutManager

    companion object {
        lateinit var viewAdapter : RecyclerView.Adapter<*>
        val upcomingItems = mutableListOf<UpcomingItem>()
        fun newInstance(): UpcomingController = UpcomingController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_upcoming, container, false)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = UpcomingAdapter(upcomingItems)

        if(!RetrofitClient.upcomingfetched)
            RetrofitClient.getUpcoming(page = 1)

        recyclerView = view.findViewById<RecyclerView>(R.id.upcoming_recyclerview).apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            addItemDecoration(HorizontalDivider(this.context))
            adapter = viewAdapter
        }

        return view
    }


}