package com.kino.ledannyyang.movies.Room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kino.ledannyyang.movies.FragmentController.WatchListController
import com.kino.ledannyyang.movies.Model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MyRoomDatabase: RoomDatabase(), CoroutineScope{
    private val job = Job()
    override val coroutineContext = Dispatchers.IO + job

    abstract fun movieDAO() : MovieDao

    companion object{
        var INSTANCE: MyRoomDatabase? = null

        fun getMyRoomDatabase(context: Context): MyRoomDatabase?{
            if(INSTANCE == null){
                synchronized(MyRoomDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, MyRoomDatabase::class.java, "myDatabase").build()
                }
            }
            return INSTANCE
        }

        fun destroyDatabase(){
            INSTANCE = null
        }
    }

    suspend fun addMovie(movie: Movie){
        launch {
            movieDAO().insert(movie)
        }.also {
            Log.d("APIQUERY", "Movie: ${movie.title} added to watchlist")
        }
    }

    suspend fun updateMovie(movie: Movie){
        launch {
            movieDAO().update(movie)
        }.also {
            Log.d("APIQUERY", "Movie: ${movie.title} updated")
        }
    }

    suspend fun deleteMovie(movie: Movie){
        launch {
            movieDAO().delete(movie)
            WatchListController.updateList(movieDAO().getWatchlist())
        }.also {
            Log.d("APIQUERY", "Movie: ${movie.title} deleted from Watchlist")
        }
    }

    suspend fun getWatchlist(){
        launch {
            val movies = movieDAO().getWatchlist()
            if(movies.isEmpty()){
                WatchListController.setEmptyView()
            }else{
                WatchListController.hideEmptyView()
                WatchListController.updateList(movies)
            }
        }
    }

    fun clearWatchlist(){
        launch {
            movieDAO().deleteWatchlist()
        }
    }
}