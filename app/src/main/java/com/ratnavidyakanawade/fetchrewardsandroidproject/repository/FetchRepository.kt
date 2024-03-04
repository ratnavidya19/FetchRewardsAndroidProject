package com.ratnavidyakanawade.fetchrewardsandroidproject.repository

import com.ratnavidyakanawade.fetchrewardsandroidproject.model.FetchItems
import com.ratnavidyakanawade.fetchrewardsandroidproject.service.FetchApi

class FetchRepository(private val fetchApi: FetchApi) {

    suspend fun getItems(): List<FetchItems> {
        return fetchApi.getItems()
    }
}