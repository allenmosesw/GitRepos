package com.allen.gitrepos.di.modules

import com.allen.gitrepos.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun contributorMainActivity(): MainActivity
}
