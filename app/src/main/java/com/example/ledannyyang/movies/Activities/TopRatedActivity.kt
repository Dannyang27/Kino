package com.example.ledannyyang.movies.Activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ledannyyang.movies.AllMightyDataController
import com.example.ledannyyang.movies.Model.Movie
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.RecyclerView.HorizontalDivider
import com.example.ledannyyang.movies.RecyclerView.MainActivityAdapter
import com.example.ledannyyang.movies.Retrofit.RetrofitClient
import com.example.ledannyyang.movies.enums.MovieTypes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TopRatedActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()
    override val coroutineContext = Dispatchers.IO + job
    private lateinit var toolbar: Toolbar
    private var gridLayoutManager: GridLayoutManager? = null
    private lateinit var viewManager : RecyclerView.LayoutManager
    var movies = mutableListOf<Movie>()

    companion object{
        lateinit var recyclerView: RecyclerView
        lateinit var viewAdapter: MainActivityAdapter

        fun updateList(movies: MutableList<Movie>){
            viewAdapter.updateList(movies)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_rated)

        gridLayoutManager = GridLayoutManager(this, 1)

        toolbar = findViewById(R.id.top_rated_toolbar)
        toolbar.title = getString(R.string.toprated)
        toolbar.setTitleTextColor(getColor(R.color.white))
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewManager = LinearLayoutManager(this)
        viewAdapter = MainActivityAdapter(gridLayoutManager, movies, MovieTypes.TOPRATED)

        recyclerView = findViewById<RecyclerView>(R.id.top_rated_recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            addItemDecoration(HorizontalDivider(this.context))
            adapter = viewAdapter
        }

        if(AllMightyDataController.topRatedMovies.isEmpty()){
            launch {
                RetrofitClient.getTopRated()
            }
        }else{
            updateList(AllMightyDataController.topRatedMovies)
            viewAdapter.notifyDataSetChanged()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?) = when(item?.itemId){
        android.R.id.home -> {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
