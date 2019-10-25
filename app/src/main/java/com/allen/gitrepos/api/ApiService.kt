package com.allen.gitrepos.api

import androidx.lifecycle.LiveData
import com.allen.gitrepos.model.Contributor
import com.allen.gitrepos.model.Repo
import com.allen.gitrepos.model.RepoSearchResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface ApiService {

    @GET("repositories")
    fun getRepo(): Observable<ApiResponse<Repo>>

    @GET("users/{login}")
    fun getUser(@Path("login") login: String): LiveData<ApiResponse<User>>

    @GET("repos/{owner}/{name}")
    fun getRepo(
        @Path("owner") owner: String,
        @Path("name") name: String
    ): LiveData<ApiResponse<Repo>>

    @GET("repos/{owner}/{name}/contributors")
    fun getContributors(
        @Path("owner") owner: String,
        @Path("name") name: String
    ): LiveData<ApiResponse<List<Contributor>>>

    @GET("search/repositories")
    fun searchRepos(@Query("q") query: String): LiveData<ApiResponse<RepoSearchResponse>>

    @GET("search/repositories")
    fun searchRepos(@Query("q") query: String, @Query("page") page: Int): Call<RepoSearchResponse>

}