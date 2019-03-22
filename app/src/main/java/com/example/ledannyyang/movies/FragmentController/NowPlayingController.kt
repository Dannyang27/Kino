package com.example.ledannyyang.movies.FragmentController

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ledannyyang.movies.Model.NowPlaying.NowPlayingItem
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.RecyclerView.HorizontalDivider
import com.example.ledannyyang.movies.RecyclerView.NowPlaying.NowPlayingAdapter
import com.example.ledannyyang.movies.Retrofit.RetrofitClient

class NowPlayingController : Fragment(){

    private lateinit var recyclerView : RecyclerView
    private lateinit var viewManager : RecyclerView.LayoutManager

    companion object {

        lateinit var viewAdapter : RecyclerView.Adapter<*>
        val nowPlayingItems = mutableListOf<NowPlayingItem>()
        fun newInstance(): NowPlayingController = NowPlayingController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_now_playing, container, false)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = NowPlayingAdapter(nowPlayingItems)

        RetrofitClient.getNowPlaying(page = 1, region = "ES")

        recyclerView = view.findViewById<RecyclerView>(R.id.now_playing_rv).apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            addItemDecoration(HorizontalDivider(this.context))
            adapter = viewAdapter
        }

        return view
    }
}