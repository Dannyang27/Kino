package com.example.ledannyyang.movies.FragmentController

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
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
    private lateinit var pref: SharedPreferences
    private lateinit var viewManager : RecyclerView.LayoutManager

    override val coroutineContext = Dispatchers.IO + job

    companion object {
        var gridLayoutManager: GridLayoutManager? = null
        lateinit var recyclerView : RecyclerView
        lateinit var decorator: HorizontalDivider
        lateinit var viewAdapter : MainActivityAdapter
        var watchlistItems = mutableListOf<Movie>()
        lateinit var layout: LinearLayout
        fun newInstance(): WatchListController = WatchListController()

        fun changeSpanCount(){
            if(gridLayoutManager?.spanCount == 1){
                gridLayoutManager?.spanCount = 3
                recyclerView.removeItemDecoration(decorator)

            }else{
                gridLayoutManager?.spanCount = 1
                recyclerView.addItemDecoration(decorator)
            }

            viewAdapter.notifyItemRangeChanged(0, viewAdapter.itemCount)
        }

        fun updateList(movies: MutableList<Movie>){
            viewAdapter.updateList(movies)
            if(movies.isEmpty()){
                setEmptyView()
            }
        }

        fun setEmptyView(){
            layout.visibility = View.VISIBLE
        }

        fun hideEmptyView(){
            layout.visibility = View.GONE
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_watchlist, container, false)

        layout = view.findViewById(R.id.watchlist_layout)

        launch {
            MyRoomDatabase.getMyRoomDatabase(activity?.applicationContext!!)?.getWatchlist()!!
        }

        pref = PreferenceManager.getDefaultSharedPreferences(activity)
        val isGridMode = pref.getBoolean("gridMode", false)

        if(isGridMode){
            gridLayoutManager = GridLayoutManager(activity, 3)
        }else{
            gridLayoutManager = GridLayoutManager(activity, 1)
        }

        viewManager = LinearLayoutManager(activity)
        viewAdapter = MainActivityAdapter(gridLayoutManager, watchlistItems)
        decorator = HorizontalDivider(activity?.applicationContext!!)

        recyclerView = view.findViewById<RecyclerView>(R.id.watchlist_rv).apply{
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
            addItemDecoration(decorator)
            adapter = viewAdapter
        }

        return view
    }
}