package com.ratnavidyakanawade.fetchrewardsandroidproject.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ratnavidyakanawade.fetchrewardsandroidproject.adapter.FetchItemAdapter
import com.ratnavidyakanawade.fetchrewardsandroidproject.model.FetchItems
import com.ratnavidyakanawade.fetchrewardsandroidproject.network.ConnectivityChecker
import com.ratnavidyakanawade.fetchrewardsandroidproject.repository.FetchRepository
import kotlinx.coroutines.launch

class FetchViewModel (private val fetchRepository: FetchRepository) : ViewModel() {
    private val _items = MutableLiveData<List<FetchItems>>()
    val items: LiveData<List<FetchItems>> get() = _items

    // Filter out any objects that have a null or blank name value
    // Used sortedWith function which returns a new list that is sorted first by listId then by item name.
    fun loadItems() {
                  viewModelScope.launch {
                      _items.value = fetchRepository.getItems()
                    .filter { !it.name.isNullOrBlank() }
                    .sortedWith(compareBy<FetchItems> { item ->
                        item.listId
                    }.thenBy { item ->
                        item.name?.let { extractInt(it) }
                    })

                  }

    }

    //converting name values from string to int to sort them properly
    private fun extractInt(name: String): Int{
        val num = name.replace("Item ", "")
        return num.toInt()
    }

}