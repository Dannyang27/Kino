package com.example.ledannyyang.movies.RecyclerView.NowPlaying

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ledannyyang.movies.R

class NowPlayingAdapter(private val myDataSet: Array<String>) :
    RecyclerView.Adapter<NowPlayingAdapter.NowPlayingViewHolder>(){

    class NowPlayingViewHolder( view : View) : RecyclerView.ViewHolder(view){
        val textView = view.findViewById(R.id.nowplaying_tv) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingAdapter.NowPlayingViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.viewholder_nowplaying, parent, false)

        return NowPlayingViewHolder(view)
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        holder.textView.text= myDataSet[position]
    }

    override fun getItemCount(): Int = myDataSet.size
}