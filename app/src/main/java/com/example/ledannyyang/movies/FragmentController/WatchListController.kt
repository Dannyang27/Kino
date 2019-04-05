package com.example.ledannyyang.movies.FragmentController

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ledannyyang.movies.Database.WatchlistDBHelper
import com.example.ledannyyang.movies.R

class WatchListController : Fragment(){

    companion object {
        fun newInstance(): WatchListController = WatchListController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_watchlist, container, false)

        val database = WatchlistDBHelper(activity?.applicationContext!!)
        database.readWatchlist()


        return view
    }


}