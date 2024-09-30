package com.ratnavidyakanawade.fetchrewardsandroidproject.dagger

import com.ratnavidyakanawade.fetchrewardsandroidproject.viewmodel.FetchViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface AppComponent {
    fun inject(viewModel: FetchViewModel)
}