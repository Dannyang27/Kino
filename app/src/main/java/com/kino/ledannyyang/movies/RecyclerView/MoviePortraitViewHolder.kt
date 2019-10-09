package com.kino.ledannyyang.movies.RecyclerView

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.kino.ledannyyang.movies.Activities.MovieDetailActivity
import com.kino.ledannyyang.movies.AllMightyDataController
import com.kino.ledannyyang.movies.R

class MoviePortraitViewHolder(view : View) : RecyclerView.ViewHolder(view){
    var id = -1
    val portrait = view.findViewById(R.id.movie_portrait) as ImageView

    init {
        view.setOnClickListener {
            val intent = Intent(it.context, MovieDetailActivity::class.java)
            intent.putExtra(AllMightyDataController.currentMovieID, id)
            it.context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(it.context as Activity).toBundle())
        }
    }
}
