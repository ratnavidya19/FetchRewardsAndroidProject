package com.ratnavidyakanawade.fetchrewardsandroidproject

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import androidx.navigation.fragment.findNavController
import com.ratnavidyakanawade.fetchrewardsandroidproject.databinding.FragmentSplashBinding
import com.ratnavidyakanawade.fetchrewardsandroidproject.viewmodel.FetchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    // View model is scoped to lifecycle of our nav_graph, which in this case would be the applications whole lifecycle
    private val fetchViewModel: FetchViewModel by navGraphViewModels(R.id.nav_graph)

    lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fetchButton.setOnClickListener {
            coroutineScope.launch {
                    temporarilyEnableLoading(4000)
                   // fetchViewModel.loadItems()
                    lifecycleScope.launch {
                        delay(400)
                        navigateToFetchRewardsListFragment()
                    }

            }
        }

    }

    private fun navigateToFetchRewardsListFragment() {
        if (findNavController().currentDestination?.id != R.id.fragment_fetch_rewards_list) {
            findNavController().navigate(SplashFragmentDirections.actionFragmentSplashToFragmentFetchRewardsList())
        }
    }

    private fun temporarilyEnableLoading(delayTime: Long) {
        binding.progressBar.visibility = View.VISIBLE
        binding.fetchButton.isEnabled = false
        lifecycleScope.launch {
            delay(delayTime)
            disableLoading()
        }
    }

    private fun disableLoading() {
        binding.progressBar.visibility = View.GONE
        binding.fetchButton.isEnabled = true
    }

    @Suppress("DEPRECATION")
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}