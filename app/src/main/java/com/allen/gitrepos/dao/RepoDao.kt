package com.allen.gitrepos.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.allen.gitrepos.model.Repo

//@Dao
//abstract class RepoDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    abstract fun insert(vararg repos: Repo)
//
//    @Query("SELECT * FROM Repo WHERE id in (:repoIds)")
//    protected abstract fun loadById(repoIds: List<Int>): LiveData<List<Repo>>
//}