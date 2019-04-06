package com.example.ledannyyang.movies.FragmentController

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ledannyyang.movies.Database.AnkoDatabase.MovieRepository
import com.example.ledannyyang.movies.Database.WatchlistDBHelper
import com.example.ledannyyang.movies.Model.Movie
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.RecyclerView.HorizontalDivider
import com.example.ledannyyang.movies.RecyclerView.MainActivityAdapter

class WatchListController : Fragment(){

    private lateinit var recyclerView : RecyclerView
    private lateinit var viewManager : RecyclerView.LayoutManager

    companion object {
        lateinit var viewAdapter : RecyclerView.Adapter<*>
        var watchlistItems = mutableListOf<Movie>()
        fun newInstance(): WatchListController = WatchListController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_watchlist, container, false)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = MainActivityAdapter(watchlistItems, activity?.application!!)

        val database = WatchlistDBHelper(activity?.applicationContext!!)
        database.readWatchlist()


        recyclerView = view.findViewById<RecyclerView>(R.id.watchlist_rv).apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            addItemDecoration(HorizontalDivider(this.context))
            adapter = viewAdapter
        }

        watchlistItems = MovieRepository(activity?.applicationContext!!).findAll()

        return view
    }


}