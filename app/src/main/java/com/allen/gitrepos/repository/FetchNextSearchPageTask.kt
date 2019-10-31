package com.allen.gitrepos.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.allen.gitrepos.api.*
import com.allen.gitrepos.db.GitDb
import com.allen.gitrepos.model.RepoSearchResult
import com.allen.gitrepos.model.Resource
import java.io.IOException

/**
 * A Task that reads the search result in the database and fetches the next page, if it has one.
 */

class FetchNextSearchPageTask constructor(
    private val query: String,
    private val apiService: ApiService,
    private val db: GitDb
) : Runnable {
    private val _liveData = MutableLiveData<Resource<Boolean>>()
    val liveData: LiveData<Resource<Boolean>> = _liveData

    override fun run() {
        val current = db.repoDao().findSearchResult(query)
        if (current == null) {
            _liveData.postValue(null)
            return
        }
        val nextPage = current.next
        if (nextPage == null) {
            _liveData.postValue(Resource.success(false))
            return
        }
        val newValue = try {
            val response = apiService.searchRepos(query, nextPage).execute()
            val apiResponse = ApiResponse.create(response)
            when (apiResponse) {
                is ApiSuccessResponse -> {
                    //we merge all repo ids into 1 list so that it is easier to
                    //fetch the result list.
                    val ids = arrayListOf<Int>()
                    ids.addAll(current.repoIds)

                    ids.addAll(apiResponse.body.items.map { it.id })
                    val merged = RepoSearchResult(
                        query, ids,
                        apiResponse.body.total,
                        apiResponse.nextPage
                    )
                    db.runInTransaction {
                        db.repoDao().insert(merged)
                        db.repoDao().insertRepos(apiResponse.body.items)
                    }
                    Resource.success(apiResponse.nextPage != null)
                }
                is ApiEmptyResponse -> {
                    Resource.success(false)
                }
                is ApiErrorResponse -> {
                    Resource.error(apiResponse.errorMessage, true)
                }

            }
        } catch (e: IOException) {
            Resource.error(e.message!!, true)
        }
        _liveData.postValue(newValue)
    }
}