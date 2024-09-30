package com.ratnavidyakanawade.fetchrewardsandroidproject.dagger

import com.ratnavidyakanawade.fetchrewardsandroidproject.Constants
import com.ratnavidyakanawade.fetchrewardsandroidproject.repository.FetchRepository
import com.ratnavidyakanawade.fetchrewardsandroidproject.service.FetchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // App-wide dependencie
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFetchApi(): FetchApi {
        return Retrofit.Builder()
            .baseUrl(Constants.FETCH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FetchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFetchRepository(api: FetchApi): FetchRepository {
        return FetchRepository(api)
    }
}