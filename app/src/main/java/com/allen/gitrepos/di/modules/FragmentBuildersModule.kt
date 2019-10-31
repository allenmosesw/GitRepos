package com.allen.gitrepos.di.modules

import com.allen.gitrepos.ui.repository.RepositoryFragment
import com.allen.gitrepos.ui.home.HomeFragment
import com.allen.gitrepos.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeRepoFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): RepositoryFragment

}
