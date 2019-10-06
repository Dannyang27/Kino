package com.example.ledannyyang.movies.RecyclerView

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.ledannyyang.movies.Activities.MovieDetailActivity
import com.example.ledannyyang.movies.AllMightyDataController
import com.example.ledannyyang.movies.R

class MoviePortraitViewHolder(view : View) : RecyclerView.ViewHolder(view){
    var id = -1
    val portrait = view.findViewById(R.id.movie_portrait) as ImageView

    init {
        view.setOnClickListener {
            val intent = Intent(it.context, MovieDetailActivity::class.java)
            intent.putExtra(AllMightyDataController.currentMovieID, id)
            it.context.startActivity(intent)
        }
    }
}
