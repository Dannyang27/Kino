package com.example.ledannyyang.movies.FragmentController

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ledannyyang.movies.Database.AnkoDatabase.AnkoDatabaseOpenHelper
import com.example.ledannyyang.movies.Database.AnkoDatabase.MovieRepository
import com.example.ledannyyang.movies.Model.Movie
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.RecyclerView.HorizontalDivider
import com.example.ledannyyang.movies.RecyclerView.MainActivityAdapter
import com.example.ledannyyang.movies.Retrofit.RetrofitClient

class NowPlayingController : Fragment(){

    private lateinit var recyclerView : RecyclerView
    private lateinit var viewManager : RecyclerView.LayoutManager

    companion object {
        lateinit var viewAdapter : RecyclerView.Adapter<*>
        var nowPlayingItems = mutableListOf<Movie>()
        fun newInstance(): NowPlayingController = NowPlayingController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_now_playing, container, false)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = MainActivityAdapter(nowPlayingItems)

        if(!RetrofitClient.nowplayingfetched) {
            val isSuccess = RetrofitClient.getNowPlaying(page = 1)
//            if(isSuccess)
//                AllMightyDataController.nowplayingMovies = nowPlayingItems
        }

        recyclerView = view.findViewById<RecyclerView>(R.id.now_playing_rv).apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            addItemDecoration(HorizontalDivider(this.context))
            adapter = viewAdapter

        }



        if(RetrofitClient.nowplayingfetched) {
            val ankoDb = AnkoDatabaseOpenHelper.getInstance(activity?.applicationContext!!)
            Log.d("APIQUERY", "Anko database created")

            val m = nowPlayingItems[0]

            createMovie(m)
            loadMovies()
            Log.d("APIQUERY", "Creating movie and inserting onto database")
        }

        //loadMovies()
        return view
    }

    private fun loadMovies(){
        activity?.applicationContext.let {
            val movies = MovieRepository(activity?.applicationContext!!).findAll()
            movies.forEach {
                Log.d("APIQUERY", "Movie Title from database is: ${it.title} and id is ${it.id}")
            }
        }
    }

    private fun createMovie(movie: Movie){
        activity?.applicationContext.let {
            MovieRepository(activity?.applicationContext!!).create(movie)
        }
    }
}