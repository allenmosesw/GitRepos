package com.allen.gitrepos.di.modules

import android.app.Application
import androidx.room.Room
import com.allen.gitrepos.api.ApiService
import com.allen.gitrepos.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModleModule::class])
class AppModule {
    @Singleton
    @Provides
    fun providesApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(ApiService::class.java)
    }


}