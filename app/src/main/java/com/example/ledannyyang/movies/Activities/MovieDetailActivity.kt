package com.example.ledannyyang.movies.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import com.example.ledannyyang.movies.FragmentMovieDetail.MovieDetailCastFragment
import com.example.ledannyyang.movies.FragmentMovieDetail.MovieDetailInfoFragment
import com.example.ledannyyang.movies.FragmentMovieDetail.MovieDetailReviewFragment
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.RecyclerView.NowPlaying.NowPlayingAdapter
import com.example.ledannyyang.movies.Retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_movie_detail.*

private const val NUM_PAGES = 3

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var mPager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
//        val movieId = intent.getIntExtra( NowPlayingAdapter.NowPlayingViewHolder.ID, -1)
//        RetrofitClient.getMovieDetail(movieId)
//
        mPager = movie_detail_viewpager
        val pageAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        mPager.adapter = pageAdapter

        val tabLayout = movie_detail_tablayout
        tabLayout.setupWithViewPager(mPager)


    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = NUM_PAGES

        override fun getItem(position: Int): Fragment{
            lateinit var fragment : Fragment
            when(position){
                0 -> fragment = MovieDetailInfoFragment()
                1 -> fragment = MovieDetailCastFragment()
                2 -> fragment = MovieDetailReviewFragment()
            }

            return fragment
        }

        override fun getPageTitle(position: Int): CharSequence? {
            var title = ""
            when(position){
                0 -> title = "INFO"
                1 -> title = "CAST"
                2 -> title = "REVIEW"
            }

            return title
        }
    }
}
