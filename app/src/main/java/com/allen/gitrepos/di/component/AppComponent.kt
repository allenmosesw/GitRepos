package com.allen.gitrepos.di.component

import android.app.Application
import com.allen.gitrepos.di.modules.AppModule
import com.allen.gitrepos.di.modules.BuildersModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidInjectionModule::class,
        BuildersModule::class,
        AppModule::class
    )
)
interface AppComponent {
    fun inject(app: Application)
}
