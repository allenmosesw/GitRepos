package com.allen.gitrepos.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.allen.gitrepos.model.Repo

@Dao
interface RepoDao {
    @Query("SELECT * FROM Repo")
    fun queryRepos(): LiveData<List<Repo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(repo: Repo)

}