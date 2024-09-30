package com.ratnavidyakanawade.fetchrewardsandroidproject

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kotlinx.coroutines.*
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ratnavidyakanawade.fetchrewardsandroidproject.adapter.FetchItemAdapter
import com.ratnavidyakanawade.fetchrewardsandroidproject.databinding.FragmentFetchRewardsListBinding
import com.ratnavidyakanawade.fetchrewardsandroidproject.viewmodel.FetchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FetchRewardsListFragment: Fragment() {

    // View model is scoped to lifecycle of our nav_graph, which in this case would be the applications whole lifecycle
    private val fetchViewModel: FetchViewModel by viewModels()

    private lateinit var adapter: FetchItemAdapter

    private lateinit var binding: FragmentFetchRewardsListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize and return view binding
        binding = FragmentFetchRewardsListBinding.inflate(inflater)
        val view = binding.root

        // Define lambda function that is passed into Adapter
        adapter = FetchItemAdapter { fetchRewardsItem ->
            Toast.makeText(requireContext(), "Item: ${fetchRewardsItem.id} Clicked", Toast.LENGTH_SHORT).show()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(view.context)
        binding.recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {

            checkNetworkAndLoadItems()

            binding.retryButton.setOnClickListener {
                checkNetworkAndLoadItems()
            }

        fetchViewModel.items.observe(viewLifecycleOwner, Observer { fetchRewardsItemList ->

            if (fetchRewardsItemList.isNotEmpty()) {
                Log.d("onViewCreated", "Data loaded successfully")
                adapter.bindData(fetchRewardsItemList)
                binding.progressBar.visibility = View.GONE // Hide progress bar after loading
                binding.networkErrorText.visibility = View.GONE // Hide error message
                binding.retryButton.visibility = View.GONE // Hide retry button on successful data load

            } else {
                Log.e("onViewCreated", "Data loading failed or empty list")
                binding.progressBar.visibility = View.GONE
                binding.networkErrorText.visibility = View.VISIBLE // Show error if data load fails
                binding.retryButton.visibility = View.VISIBLE // Show retry button when no data

            }
        })
        } catch (e: Exception) {
            Log.e("onViewCreated", "Error: ${e.message}", e)
        }
        //fetchViewModel.loadItems()
    }

    @Suppress("DEPRECATION")
    private fun checkNetworkAndLoadItems() {
        val isConnected = try {
            val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager?.activeNetwork
                val networkCapabilities = connectivityManager?.getNetworkCapabilities(network)
                networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            } else {
                val networkInfo = connectivityManager?.activeNetworkInfo
                networkInfo != null && networkInfo.isConnected
            }
        } catch (e: Exception) {
            Log.e("checkNetworkAndLoadItems", "Error checking network: ${e.message}")
            false
        }

        if (isConnected) {
            binding.progressBar.visibility = View.VISIBLE
            fetchViewModel.loadItems() // Load data from ViewModel
            binding.networkErrorText.visibility = View.GONE

        } else {
            binding.progressBar.visibility = View.GONE
            binding.networkErrorText.visibility = View.VISIBLE
            binding.retryButton.visibility = View.VISIBLE // Show retry button if no network
        }
    }

}



