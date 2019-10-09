package com.kino.ledannyyang.movies.RecyclerView.MovieDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kino.ledannyyang.movies.Model.Credit.CastItem
import com.kino.ledannyyang.movies.R
import com.kino.ledannyyang.movies.RecyclerView.CastViewHolder
import com.squareup.picasso.Picasso

class CastAdapter(private val credits: MutableList<CastItem>):
        RecyclerView.Adapter<CastViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.viewholder_cast, parent, false)

        return CastViewHolder(view)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val credit = credits[position]
        holder.creditName.text = credit.name
        holder.character.text = credit.character
        holder.id = credit.id
        holder.creditId = credit.creditId

        Picasso.get()
                .load("https://image.tmdb.org/t/p/w500/${credit?.profilePath}")
                .into(holder.portrait)
    }

    override fun getItemCount(): Int = credits.size
}


