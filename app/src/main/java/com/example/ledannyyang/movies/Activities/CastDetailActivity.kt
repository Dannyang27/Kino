package com.example.ledannyyang.movies.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.ledannyyang.movies.AllMightyDataController
import com.example.ledannyyang.movies.Model.CastDetail.CastDetail
import com.example.ledannyyang.movies.R
import com.example.ledannyyang.movies.Retrofit.RetrofitClient
import com.squareup.picasso.Picasso

class CastDetailActivity : AppCompatActivity() {

    companion object {
        lateinit var castPortrait: ImageView
        lateinit var castName : TextView
        lateinit var castBday: TextView
        lateinit var castBdayPlace: TextView

        fun setCastInfo(castDetail : CastDetail){
            val url = "https://image.tmdb.org/t/p/w500/${castDetail.profilePath}"
            Picasso.with(castPortrait.context)
                    .load(url)
                    .into(castPortrait)

            castName.text = castDetail.name
            castBday.text = castDetail.birthday
            castBdayPlace.text = castDetail.placeOfBirth
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cast_detail)

        castPortrait =  findViewById(R.id.cast_detail_portrait)
        castName = findViewById(R.id.cast_detail_name)
        castBday = findViewById(R.id.cast_detail_bday)
        castBdayPlace = findViewById(R.id.cast_detail_place_bday)

        val castId = intent?.getIntExtra( AllMightyDataController.currentCastID, -1)
        castId.let {
            RetrofitClient.getCastDetail(castId!!)
        }

        Log.d("APIQUERY", castId.toString())
    }
}
