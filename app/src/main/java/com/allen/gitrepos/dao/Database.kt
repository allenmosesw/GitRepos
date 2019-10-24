package com.allen.gitrepos.dao

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [RepoDao::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun repodao(): RepoDao
}