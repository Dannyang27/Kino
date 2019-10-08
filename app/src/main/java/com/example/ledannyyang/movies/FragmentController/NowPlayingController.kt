package com.example.ledannyyang.movies.FragmentController

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ledannyyang.movies.AllMightyDataController
import com.example.ledannyyang.movies.Model.Movie
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.RecyclerView.HorizontalDivider
import com.example.ledannyyang.movies.RecyclerView.MainActivityAdapter
import com.example.ledannyyang.movies.Retrofit.RetrofitClient
import com.example.ledannyyang.movies.Utils.RegionUtils

class NowPlayingController : Fragment(){
    private lateinit var viewManager : RecyclerView.LayoutManager
    var nowPlayingItems = mutableListOf<Movie>()
    var page = 2

    companion object {
        var gridLayoutManager: GridLayoutManager? = null
        lateinit var recyclerView : RecyclerView
        lateinit var decorator: HorizontalDivider
        lateinit var viewAdapter: MainActivityAdapter
        fun newInstance(): NowPlayingController = NowPlayingController()

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
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_now_playing, container, false)

        gridLayoutManager = GridLayoutManager(activity, 1)

        viewManager = LinearLayoutManager(activity?.applicationContext!!)
        viewAdapter = MainActivityAdapter(gridLayoutManager, nowPlayingItems)
        decorator = HorizontalDivider(activity?.applicationContext!!)

        val regionPref = PreferenceManager.getDefaultSharedPreferences(activity).getString("region", "GB")
        val region = RegionUtils.getIso3166(regionPref)
        RetrofitClient.getNowPlaying(nowPlayingItems, region = region)

        recyclerView = view.findViewById<RecyclerView>(R.id.now_playing_rv).apply{
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
            addItemDecoration(decorator)
            adapter = viewAdapter
            addOnScrollListener(object: RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                   val isBottomReached = !recyclerView.canScrollVertically(1)
                    if (isBottomReached && AllMightyDataController.nowPlayingPages > page){
                        RetrofitClient.getNowPlaying(nowPlayingItems, page = page, region = region)
                        page++
                    }
                }
            })
        }

        return view
    }
}