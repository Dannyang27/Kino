package com.kino.ledannyyang.movies.Activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kino.ledannyyang.movies.AllMightyDataController
import com.kino.ledannyyang.movies.FragmentController.NowPlayingController
import com.kino.ledannyyang.movies.FragmentController.SearchController
import com.kino.ledannyyang.movies.FragmentController.UpcomingController
import com.kino.ledannyyang.movies.FragmentController.WatchListController
import com.kino.ledannyyang.movies.R
import com.kino.ledannyyang.movies.Room.MyRoomDatabase
import com.kino.ledannyyang.movies.enums.MovieTypes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), CoroutineScope {
    private val job = Job()
    private lateinit var mainToolbar : Toolbar
    val nowPlayingFragment = NowPlayingController.newInstance()
    val upcomingFragment = UpcomingController.newInstance()
    val watchListFragment = WatchListController.newInstance()
    val searchFragment = SearchController.newInstance()
    var activeFragment: Fragment = nowPlayingFragment

    var currentLayoutIcon: Int? = null
    lateinit var pref: SharedPreferences

    override val coroutineContext = Dispatchers.IO + job


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.now_playing_item -> {
                mainToolbar.title = getString(R.string.billboard_title)
                openFragment(nowPlayingFragment)
                AllMightyDataController.movieDetailFrom = MovieTypes.NOWPLAYING
                return@OnNavigationItemSelectedListener true
            }
            R.id.upcoming_item -> {
                mainToolbar.title = getString(R.string.upcoming_title)
                openFragment(upcomingFragment)
                AllMightyDataController.movieDetailFrom = MovieTypes.UPCOMING
                return@OnNavigationItemSelectedListener true
            }
            R.id.watchlist_item -> {
                mainToolbar.title = getString(R.string.watch_list_item)
                launch { MyRoomDatabase.getMyRoomDatabase(this@MainActivity)?.getWatchlist() }
                openFragment(watchListFragment)
                AllMightyDataController.movieDetailFrom = MovieTypes.WATCHLIST
                return@OnNavigationItemSelectedListener true
            }
            R.id.search_item -> {
                mainToolbar.title = getString(R.string.search_title)
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

        mainToolbar = findViewById(R.id.main_toolbar)
        mainToolbar.title = getString(R.string.billboard_title)

        setSupportActionBar(mainToolbar)

        pref = PreferenceManager.getDefaultSharedPreferences(this)

        val navigationBar = findViewById<BottomNavigationView>(R.id.navigationBar)
        navigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        AllMightyDataController.screenWidth = displayMetrics.widthPixels

        supportFragmentManager.beginTransaction().add(R.id.container, upcomingFragment, "2").hide(upcomingFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, watchListFragment, "3").hide(watchListFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, searchFragment, "4").hide(searchFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, nowPlayingFragment, "1").commit()
    }

    override fun onBackPressed() {}


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when(item?.itemId){
        R.id.preview -> {
            val editor = pref.edit()

            if(currentLayoutIcon == R.drawable.list){
                currentLayoutIcon = R.drawable.grid
            }else{
                currentLayoutIcon = R.drawable.list
            }

            currentLayoutIcon?.let {
                item.icon = getDrawable(it)
            }

            changeControllerSpan()
            true
        }

        R.id.view_top_rated -> {
            val intent = Intent(this, TopRatedActivity::class.java)
            startActivity(intent)
            true
        }

        R.id.settings -> {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    private fun changeControllerSpan(){
        NowPlayingController.changeSpanCount()
        UpcomingController.changeSpanCount()
        WatchListController.changeSpanCount()
    }
}
