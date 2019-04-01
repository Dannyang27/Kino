package com.example.ledannyyang.movies

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import com.example.ledannyyang.movies.FragmentController.NowPlayingController
import com.example.ledannyyang.movies.FragmentController.SearchController
import com.example.ledannyyang.movies.FragmentController.UpcomingController
import com.example.ledannyyang.movies.FragmentController.WatchListController
import com.example.ledannyyang.movies.enums.MovieTypes
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var toolbar : ActionBar

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.now_playing_item -> {
                toolbar.title = getString(R.string.billboard_title)
                val nowPlayingFragment = NowPlayingController.newInstance()
                openFragment(nowPlayingFragment)
                AllMightyDataController.movieDetailFrom = MovieTypes.NOWPLAYING
                return@OnNavigationItemSelectedListener true
            }
            R.id.upcoming_item -> {
                toolbar.title = getString(R.string.upcoming_title)
                val upcomingFragment = UpcomingController.newInstance()
                openFragment(upcomingFragment)
                AllMightyDataController.movieDetailFrom = MovieTypes.UPCOMING
                return@OnNavigationItemSelectedListener true
            }
            R.id.watchlist_item -> {
                toolbar.title = getString(R.string.watch_list_item)
                val watchlistFragment = WatchListController.newInstance()
                openFragment(watchlistFragment)
                AllMightyDataController.movieDetailFrom = MovieTypes.WATCHLIST
                return@OnNavigationItemSelectedListener true
            }
            R.id.search_item -> {
                toolbar.title = getString(R.string.search_title)
                val searchFragment = SearchController.newInstance()
                openFragment(searchFragment)
                AllMightyDataController.movieDetailFrom = MovieTypes.SEARCH
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = supportActionBar!!
        navigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        lateinit var fragment: Fragment

        when(AllMightyDataController.movieDetailFrom){
            MovieTypes.NOWPLAYING -> {
                toolbar.title = getString(R.string.billboard_title)
                fragment = NowPlayingController.newInstance()
            }

            MovieTypes.UPCOMING -> {
                toolbar.title = getString(R.string.upcoming_title)
                fragment = UpcomingController.newInstance()
            }

            MovieTypes.WATCHLIST -> {
                toolbar.title = getString(R.string.watch_list_item)
                fragment = WatchListController.newInstance()
            }

            else -> {
                toolbar.title = getString(R.string.search_title)
                fragment = SearchController.newInstance()
            }
        }

        openFragment(fragment)
    }

    override fun onBackPressed() {
        //RetrofitClient.getMovieCredit("5c86e37f0e0a264310683e5a")
    }
}
