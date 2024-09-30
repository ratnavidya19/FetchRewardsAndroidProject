package com.ratnavidyakanawade.fetchrewardsandroidproject.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ratnavidyakanawade.fetchrewardsandroidproject.adapter.FetchItemAdapter
import com.ratnavidyakanawade.fetchrewardsandroidproject.model.FetchItems
import com.ratnavidyakanawade.fetchrewardsandroidproject.repository.FetchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FetchViewModel @Inject constructor(
    private val repository: FetchRepository
)  : ViewModel() {

    private val _items = MutableLiveData<List<FetchItems>>()
    val items: LiveData<List<FetchItems>> get() = _items

    // Filter out any objects that have a null or blank name value
    // Used sortedWith function which returns a new list that is sorted first by listId then by item name.
    fun loadItems() {
                  viewModelScope.launch(Dispatchers.IO) {
                      val fetchItems = repository.getItems()
                          .filter { !it.name.isNullOrBlank() }
                          .sortedWith(compareBy<FetchItems> { it.listId }
                              .thenBy { item ->
                        item.name?.let { extractInt(it) } ?: Int.MAX_VALUE
                    })

                      // Use postValue to update LiveData from the background thread
                      _items.postValue(fetchItems)

                  }

    }

    //converting name values from string to int to sort them properly
    fun extractInt(name: String): Int{
        return try {
            val num = name.replace("Item ", "")
            return num.toIntOrNull() ?: Int.MAX_VALUE
            //return num.toInt()
        }catch (e: Exception) {
            return Int.MAX_VALUE // Default to a large value if parsing fails
        }
    }

}