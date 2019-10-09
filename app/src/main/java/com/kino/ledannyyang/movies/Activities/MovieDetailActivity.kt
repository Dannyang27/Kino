package com.kino.ledannyyang.movies.Activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.kino.ledannyyang.movies.FragmentMovieDetail.MovieDetailCastFragment
import com.kino.ledannyyang.movies.FragmentMovieDetail.MovieDetailInfoFragment
import com.kino.ledannyyang.movies.FragmentMovieDetail.MovieDetailReviewFragment
import com.kino.ledannyyang.movies.R

private const val NUM_PAGES = 3

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var mPager : ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieToolbar = findViewById<Toolbar>(R.id.movie_detail_toolbar)
        setSupportActionBar(movieToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        mPager = findViewById(R.id.movie_detail_viewpager)
        val pageAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        mPager.adapter = pageAdapter

        tabLayout = findViewById(R.id.movie_detail_tablayout)
        tabLayout.setupWithViewPager(mPager)
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = NUM_PAGES

        override fun getItem(position: Int): Fragment {
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

    override fun onOptionsItemSelected(item: MenuItem?) = when(item?.itemId){
        android.R.id.home -> {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
