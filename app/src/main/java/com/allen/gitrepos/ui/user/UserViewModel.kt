package com.allen.gitrepos.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.allen.gitrepos.model.Repo
import com.allen.gitrepos.model.Resource
import com.allen.gitrepos.model.User
import com.allen.gitrepos.repository.UserRepository
import com.allen.gitrepos.repository.RepoRepository
import com.allen.gitrepos.utils.AbsentLiveData
import javax.inject.Inject

class UserViewModel
@Inject constructor(usesRepository: UserRepository, repoRepository: RepoRepository) : ViewModel() {
    private val _login = MutableLiveData<String>()
    val login: LiveData<String>
        get() = _login
    val repository: LiveData<Resource<List<Repo>>> = Transformations
        .switchMap(_login) { login ->
            if (login == null) {
                AbsentLiveData.create()
            } else {
                repoRepository.loadRepos(login)
            }
        }

    val user: LiveData<Resource<User>> = Transformations
        .switchMap(_login) { login ->
            if (login == null) {
                AbsentLiveData.create()
            } else {
                usesRepository.loadUser(login)
            }
        }

    fun setLogin(login: String?) {
        if (_login.value != login) {
            _login.value = login
        }
    }

    fun retry() {
        _login.value?.let {
            _login.value = it
        }
    }
}