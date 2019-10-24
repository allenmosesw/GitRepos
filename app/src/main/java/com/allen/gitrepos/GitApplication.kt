package com.allen.gitrepos

import android.app.Application
import com.allen.gitrepos.di.component.DaggerAppComponent
import com.allen.gitrepos.di.modules.AppModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class GitApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder().appModule(AppModule(this)).build().inject(this)
    }

    override fun androidInjector() = activityInjector

}