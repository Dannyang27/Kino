package com.example.ledannyyang.movies.FragmentMovieDetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ledannyyang.movies.R

class MovieDetailInfoFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.movie_detail_info, container, false)
    }
}