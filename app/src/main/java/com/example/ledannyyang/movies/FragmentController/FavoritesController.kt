package com.example.ledannyyang.movies.FragmentController

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ledannyyang.movies.R

class FavoritesController : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }
    companion object {
        fun newInstance(): FavoritesController = FavoritesController()
    }
}