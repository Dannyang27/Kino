package com.kino.ledannyyang.movies.FragmentMovieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kino.ledannyyang.movies.AllMightyDataController
import com.kino.ledannyyang.movies.Model.Credit.CastItem
import com.kino.ledannyyang.movies.R
import com.kino.ledannyyang.movies.RecyclerView.HorizontalDivider
import com.kino.ledannyyang.movies.RecyclerView.MovieDetail.CastAdapter
import com.kino.ledannyyang.movies.Retrofit.RetrofitClient

class MovieDetailCastFragment : Fragment(){
    private lateinit var castRecyclerView : RecyclerView
    private lateinit var castViewManager : RecyclerView.LayoutManager

    companion object {
        lateinit var castAdapter : RecyclerView.Adapter<*>
        var credits = mutableListOf<CastItem>()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view =  inflater.inflate(R.layout.movie_detail_cast, container, false)

        val movieId = activity?.intent?.getIntExtra( AllMightyDataController.currentMovieID, -1)

        castViewManager = LinearLayoutManager(activity)
        castAdapter = CastAdapter(credits)

        castRecyclerView = view.findViewById<RecyclerView>(R.id.cast_recyclerview).apply{
            setHasFixedSize(true)
            layoutManager = castViewManager
            addItemDecoration(HorizontalDivider(this.context))
            adapter = castAdapter
        }

        movieId.let {
            if(AllMightyDataController.movieCastMap.containsKey(movieId)){
                credits = AllMightyDataController.movieCastMap[movieId]!!
                castAdapter.notifyDataSetChanged()
            }else{
                credits.clear()
                RetrofitClient.getCredits(movieId!!)
            }
        }

        return view
    }
}