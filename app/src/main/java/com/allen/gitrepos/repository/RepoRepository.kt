package com.allen.gitrepos.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.allen.gitrepos.AppExectors
import com.allen.gitrepos.api.ApiResponse
import com.allen.gitrepos.api.ApiService
import com.allen.gitrepos.api.ApiSuccessResponse
import com.allen.gitrepos.db.GitDb
import com.allen.gitrepos.db.RepoDao
import com.allen.gitrepos.model.*
import com.allen.gitrepos.utils.AbsentLiveData
import com.allen.gitrepos.utils.LiveDataCallAdapter
import com.allen.gitrepos.utils.RateLimiter
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(
    private val appExecutors: AppExectors,
    private val db: GitDb,
    private val repoDao: RepoDao,
    private val apiService: ApiService
) {
    private val reposLisRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun loadRepos(owner: String): LiveData<Resource<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>, List<Repo>>(appExecutors) {
            override fun saveCallResult(item: List<Repo>) {
                repoDao.insertRepos(item)
            }

            override fun shouldFetch(data: List<Repo>?): Boolean {
                return data == null || data.isEmpty() || reposLisRateLimit.shouldFetch(owner)
            }

            override fun loadFromDb() = repoDao.loadRepositories(owner)

            override fun createCall() = apiService.getRepos(owner)

            override fun onFetchFailed() {
                reposLisRateLimit.reset(owner)
            }
        }.asLiveData()
    }

    fun loadRepo(owner: String, name: String): LiveData<Resource<Repo>> {
        return object : NetworkBoundResource<Repo, Repo>(appExecutors) {
            override fun shouldFetch(data: Repo?) = data == null

            override fun loadFromDb() = repoDao.load(
                ownerLogin = owner,
                name = name
            )


            override fun createCall() = apiService.getRepo(
                owner = owner,
                name = name
            )

            override fun saveCallResult(item: Repo) {
                repoDao.insert(item)
            }
        }.asLiveData()
    }

    fun loadContributors(owner: String, name: String): LiveData<Resource<List<Contributor>>> {
        return object : NetworkBoundResource<List<Contributor>, List<Contributor>>(appExecutors) {
            override fun saveCallResult(item: List<Contributor>) {
                item.forEach {
                    it.repoName = name
                    it.repoOwner = owner
                }
                db.runInTransaction {
                    repoDao.createRepoIdNotExists(
                        Repo(
                            id = Repo.UNKNOWN_ID,
                            name = name,
                            fullName = "$owner/$name",
                            description = "",
                            owner = Repo.Owner(owner, null),
                            stars = 0
                        )

                    )
                    repoDao.insertContributors(item)
                }
            }

            override fun shouldFetch(data: List<Contributor>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() = repoDao.loadContributors(owner, name)

            override fun createCall() = apiService.getContributors(owner, name)
        }.asLiveData()
    }

    fun searchNextPage(query: String): LiveData<Resource<Boolean>> {
        val fetchNextSearchPageTask = FetchNextSearchPageTask(
            query = query,
            apiService = apiService,
            db = db
        )
        appExecutors.networkIO().execute(fetchNextSearchPageTask)
        return fetchNextSearchPageTask.liveData
    }

    fun search(query: String): LiveData<Resource<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>, RepoSearchResponse>(appExecutors) {

            override fun saveCallResult(item: RepoSearchResponse) {
                val repoIds = item.items.map { it.id }
                val repoSearchResult = RepoSearchResult(
                    query = query,
                    repoIds = repoIds,
                    totalCount = item.total,
                    next = item.nextPage
                )
                db.runInTransaction {
                    repoDao.insertRepos(item.items)
                    repoDao.insert(repoSearchResult)
                }
            }

            override fun shouldFetch(data: List<Repo>?) = data == null

            override fun loadFromDb(): LiveData<List<Repo>> {
                return Transformations.switchMap(repoDao.search(query)) { searchData ->
                    if (searchData == null) {
                        AbsentLiveData.create()
                    } else {
                        repoDao.loadOrdered(searchData.repoIds)
                    }
                }
            }

            override fun createCall() = apiService.searchRepos(query)

            override fun processResponse(response: ApiSuccessResponse<RepoSearchResponse>)
                    : RepoSearchResponse {
                val body = response.body
                body.nextPage = response.nextPage
                return body
            }
        }.asLiveData()
    }

}


