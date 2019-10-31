package com.allen.gitrepos.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.allen.gitrepos.model.Contributor
import com.allen.gitrepos.model.Repo
import com.allen.gitrepos.model.RepoSearchResult
import com.allen.gitrepos.model.User

@Database(
    entities = [
        User::class,
        Repo::class,
        Contributor::class,
        RepoSearchResult::class],
    version = 1,
    exportSchema = false
)
abstract class GitDb : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun repoDao(): RepoDao
}