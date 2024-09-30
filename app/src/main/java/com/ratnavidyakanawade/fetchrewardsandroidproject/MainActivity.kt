package com.ratnavidyakanawade.fetchrewardsandroidproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ratnavidyakanawade.fetchrewardsandroidproject.adapter.FetchItemAdapter
import com.ratnavidyakanawade.fetchrewardsandroidproject.databinding.ActivityMainBinding
import com.ratnavidyakanawade.fetchrewardsandroidproject.viewmodel.FetchViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }


}