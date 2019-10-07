package com.example.ledannyyang.movies.FragmentController

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ledannyyang.movies.Model.Movie
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.RecyclerView.HorizontalDivider
import com.example.ledannyyang.movies.RecyclerView.MainActivityAdapter
import com.example.ledannyyang.movies.Room.MyRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WatchListController : Fragment(), CoroutineScope{

    private val job = Job()
    private lateinit var recyclerView : RecyclerView
    private lateinit var viewManager : RecyclerView.LayoutManager

    override val coroutineContext = Dispatchers.IO + job

    companion object {
        lateinit var viewAdapter : MainActivityAdapter
        var watchlistItems = mutableListOf<Movie>()
        fun newInstance(): WatchListController = WatchListController()

        fun updateList(movies: MutableList<Movie>){
            viewAdapter.updateList(movies)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_watchlist, container, false)

        launch {
            MyRoomDatabase.getMyRoomDatabase(activity?.applicationContext!!)?.getWatchlist()!!
        }

        viewManager = LinearLayoutManager(activity)
        viewAdapter = MainActivityAdapter(watchlistItems)

        recyclerView = view.findViewById<RecyclerView>(R.id.watchlist_rv).apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            addItemDecoration(HorizontalDivider(this.context))
            adapter = viewAdapter
        }

        return view
    }
}