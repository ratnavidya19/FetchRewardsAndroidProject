package com.ratnavidyakanawade.fetchrewardsandroidproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ratnavidyakanawade.fetchrewardsandroidproject.network.ConnectivityChecker
import com.ratnavidyakanawade.fetchrewardsandroidproject.repository.FetchRepository

class FetchViewModelFactory(private val fetchRepository: FetchRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FetchViewModel(fetchRepository) as T
    }
}