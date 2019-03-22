package com.example.ledannyyang.movies

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import com.example.ledannyyang.movies.FragmentController.NowPlayingController
import com.example.ledannyyang.movies.FragmentController.SearchController
import com.example.ledannyyang.movies.FragmentController.UpcomingController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var toolbar : ActionBar

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.now_playing_item -> {
                toolbar.title = "BillBoard"
                val nowPlayingFragment = NowPlayingController.newInstance()
                openFragment(nowPlayingFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.upcoming_item -> {
                toolbar.title = "Upcoming Movies"
                val upcomingFragment = UpcomingController.newInstance()
                openFragment(upcomingFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.search_item -> {
                toolbar.title = "Search"
                val searchFragment = SearchController.newInstance()
                openFragment(searchFragment)
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
        val bottomNavigation : BottomNavigationView = navigationBar
        navigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        toolbar.title = "Now Playing"
        val nowPlaying = NowPlayingController.newInstance()
        openFragment(nowPlaying)




//        val nowPlaying = RetrofitService.INSTANCE.getNowPlaying(region = "ES")
//        val topRated = RetrofitService.INSTANCE.getTopRated()
//        val upcoming = RetrofitService.INSTANCE.getUpcoming()
//        val similarMovie = RetrofitService.INSTANCE.getSimilarMoviesById(297802)
//        val recommendedMovie = RetrofitService.INSTANCE.getRecommendedMoviesById(297802)
//        val reviews = RetrofitService.INSTANCE.getReviewsById(297802)
//        val videos = RetrofitService.INSTANCE.getVideosById(297802)
//        val socialNetwork = RetrofitService.INSTANCE.getExternalSocialNetwork(297802)
//        val credit = RetrofitService.INSTANCE.getCredits(297802)
//        val movieDetails = RetrofitService.INSTANCE.getMovieDetail(297802)
//        val castDetail = RetrofitService.INSTANCE.getCastDetail(117642)

    }
}
