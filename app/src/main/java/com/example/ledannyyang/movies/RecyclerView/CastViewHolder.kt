package com.example.ledannyyang.movies.RecyclerView

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ledannyyang.movies.Activities.CastDetailActivity
import com.example.ledannyyang.movies.AllMightyDataController
import com.example.ledannyyang.movies.R

class CastViewHolder(view : View) : RecyclerView.ViewHolder(view){
    var id = -1
    var creditId = ""
    val portrait = view.findViewById(R.id.cast_portrait) as ImageView
    val creditName = view.findViewById(R.id.movie_cast_name) as TextView
    val character = view.findViewById(R.id.movie_cast_character) as TextView

    init {
        view.setOnClickListener {
            Toast.makeText(portrait.context, creditId, Toast.LENGTH_SHORT).show()
            val intent = Intent(it.context, CastDetailActivity::class.java)
            intent.putExtra(AllMightyDataController.currentCastID, id)
            intent.putExtra(AllMightyDataController.currentCreditID, creditId)
            it.context.startActivity(intent)
        }
    }
}
