package com.example.ledannyyang.movies.Activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ledannyyang.movies.AllMightyDataController
import com.example.ledannyyang.movies.Model.CastDetail.CastDetail
import com.example.ledannyyang.movies.Model.PortraitMovie.PortraitMovie
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.RecyclerView.MovieDetail.MoviePortraitAdapter
import com.example.ledannyyang.movies.Retrofit.RetrofitClient
import com.squareup.picasso.Picasso

class CastDetailActivity : AppCompatActivity() {
    private lateinit var castRecyclerView : RecyclerView
    private lateinit var castViewManager : RecyclerView.LayoutManager

    companion object {
        lateinit var castPortrait: ImageView
        lateinit var castName : TextView
        lateinit var castBday: TextView
        lateinit var castBdayPlace: TextView
        lateinit var castBiography: TextView

        lateinit var castAdapter : MoviePortraitAdapter
        var filmographyItems = mutableListOf<PortraitMovie>()

        fun setCastInfo(castDetail : CastDetail){
            val url = "https://image.tmdb.org/t/p/w500/${castDetail.profilePath}"

            Picasso.get()
                    .load(url)
                    .into(castPortrait)

            castName.text = castDetail.name
            castBday.text = castDetail.birthday
            castBdayPlace.text = castDetail.placeOfBirth
            castBiography.text = castDetail.biography
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cast_detail)

        val castId = intent?.getIntExtra( AllMightyDataController.currentCastID, -1)
        val creditId = intent?.getStringExtra( AllMightyDataController.currentCreditID)
        castPortrait =  findViewById(R.id.cast_detail_portrait)
        castName = findViewById(R.id.cast_detail_name)
        castBday = findViewById(R.id.cast_detail_bday)
        castBdayPlace = findViewById(R.id.cast_detail_place_bday)
        castBiography = findViewById(R.id.cast_detail_biography)

        castViewManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        castAdapter = MoviePortraitAdapter(filmographyItems)

        castRecyclerView = findViewById<RecyclerView>(R.id.cast_detail_recyclerview).apply{
            setHasFixedSize(true)
            layoutManager = castViewManager
            adapter = castAdapter
        }

        loadCastInfo(castId)
        loadCastFilmography(castId,creditId)
    }

    fun loadCastInfo(castId: Int?){
        castId.let {
            if(AllMightyDataController.castInfoMap.containsKey(castId)){
                setCastInfo(AllMightyDataController.castInfoMap[castId]!!)
            }else{
                RetrofitClient.getCastDetail(castId!!)
            }
        }
    }

    fun loadCastFilmography(castId: Int?, creditId : String?){
        creditId.let {
            castId.let {
                if(AllMightyDataController.castFilmographyMap.containsKey(castId)){
                    filmographyItems = AllMightyDataController.castFilmographyMap[castId]?.toMutableList()!!
                    castAdapter.notifyDataSetChanged()

                }else{
                    creditId.let {
                        filmographyItems.clear()
                        if (RetrofitClient.getMovieCredit(creditId!!))
                            AllMightyDataController.castFilmographyMap.plus(Pair(castId, filmographyItems))
                    }
                }
            }
        }
    }
}
