package com.kino.ledannyyang.movies.Utils

import android.content.Context
import android.net.ConnectivityManager
import java.net.InetAddress

object ConnectionUtils {
    fun isConnectedToNetwork(context: Context): Boolean{
        val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        connManager?.let {
            val activeNetwork = connManager.activeNetworkInfo
            activeNetwork?.let {
                return it.isConnectedOrConnecting
            }
        }

        return false
    }

    fun isInternetAvailable(): Boolean{
        try{
            val address = InetAddress.getByName("www.google.com")
            return (address.toString() != "")
        }catch (e: Exception){ }
        return false
    }
}