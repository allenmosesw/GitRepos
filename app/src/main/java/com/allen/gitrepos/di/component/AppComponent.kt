package com.allen.gitrepos.di.component

import android.app.Application
import com.allen.gitrepos.GitApplication
import com.allen.gitrepos.di.modules.AppModule
import com.allen.gitrepos.di.modules.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        MainActivityModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: GitApplication)
}
