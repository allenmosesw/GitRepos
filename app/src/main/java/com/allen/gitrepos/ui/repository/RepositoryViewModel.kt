package com.allen.gitrepos.ui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.allen.gitrepos.model.Contributor
import com.allen.gitrepos.model.Repo
import com.allen.gitrepos.model.Resource
import com.allen.gitrepos.repository.RepoRepository
import com.allen.gitrepos.utils.AbsentLiveData
import javax.inject.Inject

class RepositoryViewModel @Inject constructor(repository: RepoRepository) : ViewModel() {
    private val _repoId: MutableLiveData<RepoId> = MutableLiveData()

    val repoId: LiveData<RepoId>
        get() = _repoId
    val repo: LiveData<Resource<Repo>> = Transformations
        .switchMap(_repoId) { input ->
            input.ifExists { owner, name ->
                repository.loadRepo(owner, name)
            }
        }

    val contributors: LiveData<Resource<List<Contributor>>> = Transformations
        .switchMap(_repoId) { input ->
            input.ifExists { owner, name ->
                repository.loadContributors(owner, name)
            }
        }

    fun retry() {
        val owner = repoId.value?.owner
        val name = _repoId.value?.name
        if (owner != null && name != null) {
            _repoId.value = RepoId(owner, name)
        }
    }

    fun setId(owner: String,name: String){
        val update = RepoId(owner, name)
        if(_repoId.value == update){
            return
        }
        _repoId.value = update
    }
}

data class RepoId(val owner: String, val name: String) {
    fun <T> ifExists(f: (String, String) -> LiveData<T>): LiveData<T> {
        return if (owner.isBlank() || name.isBlank()) {
            AbsentLiveData.create()
        } else {
            f(owner, name)
        }
    }

}
