package com.ratnavidyakanawade.fetchrewardsandroidproject

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ratnavidyakanawade.fetchrewardsandroidproject.adapter.FetchItemAdapter
import com.ratnavidyakanawade.fetchrewardsandroidproject.databinding.ActivityMainBinding
import com.ratnavidyakanawade.fetchrewardsandroidproject.network.ConnectivityCheckerImpl
import com.ratnavidyakanawade.fetchrewardsandroidproject.repository.FetchRepository
import com.ratnavidyakanawade.fetchrewardsandroidproject.service.FetchApi
import com.ratnavidyakanawade.fetchrewardsandroidproject.viewmodel.FetchViewModel
import com.ratnavidyakanawade.fetchrewardsandroidproject.viewmodel.FetchViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FetchViewModel
    private lateinit var adapter: FetchItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var actionBar = getSupportActionBar()

        // showing the back button in action bar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressBar.visibility = View.VISIBLE
        val connectivityChecker = ConnectivityCheckerImpl(this)
        if (!connectivityChecker.isConnected()) {
            binding.progressBar.visibility = View.GONE
            binding.txtNetworkConnection.visibility = View.VISIBLE
            //Toast.makeText(this@MainActivity, "No internet connection",Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Toast.makeText(this@MainActivity, "Internet connected",Toast.LENGTH_SHORT).show();
            binding.txtNetworkConnection.visibility = View.GONE
            val apiService = Retrofit.Builder()
                .baseUrl(Constants.FETCH_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FetchApi::class.java)
            val repository = FetchRepository(apiService)
            viewModel = ViewModelProvider(this, FetchViewModelFactory(repository))[FetchViewModel::class.java]

            //displaying particular item id in toast on click event
            adapter = FetchItemAdapter { fetchItem ->
                Toast.makeText(this@MainActivity, "ID: ${fetchItem.id} Clicked", Toast.LENGTH_SHORT).show()
            }

            viewModel.items.observe(this, { items ->
                adapter.items = items
                binding.progressBar.visibility = View.GONE

            })

            viewModel.loadItems();
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
            binding.recyclerView.adapter = adapter

        }

    }

    // this event will enable the back function to the button on press
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}