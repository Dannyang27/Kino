package com.kino.ledannyyang.movies.FragmentController

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kino.ledannyyang.movies.AllMightyDataController
import com.kino.ledannyyang.movies.Model.Movie
import com.kino.ledannyyang.movies.R
import com.kino.ledannyyang.movies.RecyclerView.HorizontalDivider
import com.kino.ledannyyang.movies.RecyclerView.MainActivityAdapter
import com.kino.ledannyyang.movies.Retrofit.RetrofitClient
import com.kino.ledannyyang.movies.Utils.ConnectionUtils
import com.kino.ledannyyang.movies.Utils.RegionUtils
import com.kino.ledannyyang.movies.enums.MovieTypes
import org.jetbrains.anko.toast

class UpcomingController : Fragment(){
    private lateinit var pref: SharedPreferences
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var viewManager : RecyclerView.LayoutManager
    private val upcomingItems = mutableListOf<Movie>()
    var page = 2

    companion object {
        var gridLayoutManager: GridLayoutManager? = null
        lateinit var decorator: HorizontalDivider
        lateinit var recyclerView : RecyclerView
        lateinit var viewAdapter : MainActivityAdapter
        fun newInstance(): UpcomingController = UpcomingController()

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
        val view =  inflater.inflate(R.layout.fragment_upcoming, container, false)

        swipeRefresh = view.findViewById(R.id.upcoming_swiperefresh)
        gridLayoutManager = GridLayoutManager(activity, 1)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = MainActivityAdapter(gridLayoutManager, upcomingItems, MovieTypes.UPCOMING)
        decorator = HorizontalDivider(activity?.applicationContext!!)

        pref = PreferenceManager.getDefaultSharedPreferences(activity)
        val regionPref = pref.getString("region", "GB")
        val region = RegionUtils.getIso3166(regionPref)
        RetrofitClient.getUpcoming(upcomingItems, region = region)

        recyclerView = view.findViewById<RecyclerView>(R.id.upcoming_recyclerview).apply{
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
            addItemDecoration(decorator)
            adapter = viewAdapter

            addOnScrollListener(object: RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val isBottomReached = !recyclerView.canScrollVertically(1)
                    if (isBottomReached && AllMightyDataController.upcomingMoviesPages > page){
                        RetrofitClient.getUpcoming(upcomingItems, page = page, region = region)
                        page++
                    }
                }
            })
        }

        swipeRefresh.setOnRefreshListener {
            if(ConnectionUtils.isConnectedToNetwork(activity?.applicationContext!!)) {
                upcomingItems.clear()
                viewAdapter.notifyDataSetChanged()
                page = 1
                RetrofitClient.getUpcoming(upcomingItems, page = page, region = region)
                page++
                swipeRefresh.isRefreshing = false
            }else{
                val context = swipeRefresh.context
                context.toast(context.getString(R.string.no_internet))
            }
        }
        return view
    }
}