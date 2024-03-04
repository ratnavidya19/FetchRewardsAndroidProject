package com.ratnavidyakanawade.fetchrewardsandroidproject.service

import com.ratnavidyakanawade.fetchrewardsandroidproject.Constants
import com.ratnavidyakanawade.fetchrewardsandroidproject.model.FetchItems
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface FetchApi {

    @GET("hiring.json")
    suspend fun getItems(): List<FetchItems>

}