package com.allen.gitrepos.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.allen.gitrepos.di.ViewModelKey
import com.allen.gitrepos.ui.repository.RepositoryViewModel
import com.allen.gitrepos.ui.search.SearchViewModel
import com.allen.gitrepos.ui.user.UserViewModel
import com.allen.gitrepos.viewmodel.GitRepoViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserViewModel(userViewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RepositoryViewModel::class)
    abstract fun bindRepoViewModel(repositoryViewModel: RepositoryViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory : GitRepoViewModelFactory) : ViewModelProvider.Factory
}