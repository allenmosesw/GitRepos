package com.allen.gitrepos.di.modules

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = app
//
//    @Provides
//    @Singleton
//    fun provideGitHubDatabase(app: Application): Database = Room.databaseBuilder(
//        app,
//        Database::class.java,
//        "github_db"
//    ).build()
//
//    @Provides
//    @Singleton
//    fun provideRepoDao(database: Database): RepoDao = database.repodao()
}