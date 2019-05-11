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
    val nowPlayingFragment = NowPlayingController.newInstance()
    val upcomingFragment = UpcomingController.newInstance()
    val watchListFragment = WatchListController.newInstance()
    val searchFragment = SearchController.newInstance()
    var activeFragment: Fragment = nowPlayingFragment

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.now_playing_item -> {
                toolbar.title = getString(R.string.billboard_title)
                openFragment(nowPlayingFragment)
                AllMightyDataController.movieDetailFrom = MovieTypes.NOWPLAYING
                return@OnNavigationItemSelectedListener true
            }
            R.id.upcoming_item -> {
                toolbar.title = getString(R.string.upcoming_title)
                openFragment(upcomingFragment)
                AllMightyDataController.movieDetailFrom = MovieTypes.UPCOMING
                return@OnNavigationItemSelectedListener true
            }
            R.id.watchlist_item -> {
                toolbar.title = getString(R.string.watch_list_item)
                openFragment(watchListFragment)
                AllMightyDataController.movieDetailFrom = MovieTypes.WATCHLIST
                return@OnNavigationItemSelectedListener true
            }
            R.id.search_item -> {
                toolbar.title = getString(R.string.search_title)
                openFragment(searchFragment)
                AllMightyDataController.movieDetailFrom = MovieTypes.SEARCH
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().hide(activeFragment).show(fragment).commit()
        activeFragment = fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = supportActionBar!!
        toolbar.title = getString(R.string.billboard_title)

        navigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        supportFragmentManager.beginTransaction().add(R.id.container, upcomingFragment, "2").hide(upcomingFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, watchListFragment, "3").hide(watchListFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, searchFragment, "4").hide(searchFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, nowPlayingFragment, "1").commit()
    }

    override fun onBackPressed() {
        //RetrofitClient.getMovieCredit("5c86e37f0e0a264310683e5a")
    }
}
