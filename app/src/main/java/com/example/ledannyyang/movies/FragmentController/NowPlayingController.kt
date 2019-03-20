package com.example.ledannyyang.movies.FragmentController

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.RecyclerView.NowPlaying.NowPlayingAdapter
import com.example.ledannyyang.movies.Retrofit.RetrofitService
import kotlinx.android.synthetic.main.fragment_now_playing.*

class NowPlayingController : Fragment(){

    private lateinit var recyclerView : RecyclerView
    private lateinit var viewAdapter : RecyclerView.Adapter<*>
    private lateinit var viewManager : RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_now_playing, container, false)

        val nowPlaying = RetrofitService.INSTANCE.getNowPlaying(region = "ES")

        viewManager = LinearLayoutManager(activity)
        viewAdapter = NowPlayingAdapter(arrayOf("Banana", "Tomato", "Strawberry", "Milkshake", "Tuna"))
        recyclerView = view.findViewById<RecyclerView>(R.id.now_playing_rv).apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return view
    }

    companion object {
        fun newInstance(): NowPlayingController = NowPlayingController()
    }
}