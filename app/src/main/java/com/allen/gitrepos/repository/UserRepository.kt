package com.allen.gitrepos.repository

import androidx.lifecycle.LiveData
import com.allen.gitrepos.AppExectors
import com.allen.gitrepos.api.ApiResponse
import com.allen.gitrepos.api.ApiService
import com.allen.gitrepos.db.UserDao
import com.allen.gitrepos.model.Resource
import com.allen.gitrepos.model.User
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles User objects.
 */
@Singleton
class UserRepository @Inject constructor(
    private val appExectors: AppExectors,
    private val userDao: UserDao,
    private val apiService: ApiService
) {

    fun loadUser(login: String): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, User>(appExectors) {
            override fun saveCallResult(item: User) {
                userDao.insert(item)
            }

            override fun shouldFetch(data: User?) = data == null

            override fun loadFromDb() = userDao.findByLogin(login)

            override fun createCall() = apiService.getUser(login)

        }.asLiveData()
    }


}