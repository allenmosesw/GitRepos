package com.allen.gitrepos

import android.app.Application
import com.allen.gitrepos.di.AppInjector
import com.allen.gitrepos.di.component.DaggerAppComponent
import com.allen.gitrepos.di.modules.AppModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.BuildConfig
import timber.log.Timber
import javax.inject.Inject

class GitApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppInjector.init(this)
    }

    override fun androidInjector() = activityInjector

}