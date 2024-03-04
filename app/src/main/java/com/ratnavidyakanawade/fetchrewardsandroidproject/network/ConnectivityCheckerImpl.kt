package com.ratnavidyakanawade.fetchrewardsandroidproject.network

import android.content.Context
import android.net.ConnectivityManager

//class to check if device is connected to a network
class ConnectivityCheckerImpl (private val context: Context) : ConnectivityChecker {

    override fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}