package com.kino.ledannyyang.movies.FragmentController

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kino.ledannyyang.movies.Model.Movie
import com.kino.ledannyyang.movies.R
import com.kino.ledannyyang.movies.RecyclerView.HorizontalDivider
import com.kino.ledannyyang.movies.RecyclerView.MainActivityAdapter
import com.kino.ledannyyang.movies.Retrofit.RetrofitClient
import com.kino.ledannyyang.movies.enums.MovieTypes

class SearchController : Fragment(){

    private var gridLayoutManager: GridLayoutManager? = null
    private lateinit var recyclerView : RecyclerView
    private lateinit var viewManager : RecyclerView.LayoutManager
    var movies = mutableListOf<Movie>()

    companion object {
        fun newInstance(): SearchController = SearchController()
        lateinit var layout: LinearLayout
        lateinit var viewAdapter: MainActivityAdapter
        fun updateList(movies: MutableList<Movie>){
            viewAdapter.updateList(movies)
        }

        fun setEmptyView(){
            layout.visibility = View.VISIBLE
        }

        fun hideEmptyView(){
            layout.visibility = View.GONE
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        layout = view.findViewById(R.id.search_layout)
        gridLayoutManager = GridLayoutManager(activity, 1)

        val searchview = view.findViewById<SearchView>(R.id.search_searchview)
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideEmptyView()
                movies.clear()
                RetrofitClient.getSearchMovie(movies, query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        viewManager = LinearLayoutManager(activity?.applicationContext!!)
        viewAdapter = MainActivityAdapter(gridLayoutManager, movies, MovieTypes.SEARCH)

        recyclerView = view.findViewById<RecyclerView>(R.id.search_recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            addItemDecoration(HorizontalDivider(this.context))
            adapter = viewAdapter
        }

        return view
    }

}