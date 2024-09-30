package com.ratnavidyakanawade.fetchrewardsandroidproject.repository

import com.ratnavidyakanawade.fetchrewardsandroidproject.model.FetchItems
import com.ratnavidyakanawade.fetchrewardsandroidproject.service.FetchApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchRepository @Inject constructor(private val fetchApi: FetchApi){

    suspend fun getItems(): List<FetchItems> {
        return withContext(Dispatchers.IO) {
            fetchApi.getItems()
        }
    }

}