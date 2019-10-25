package com.allen.gitrepos.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.allen.gitrepos.R
import com.allen.gitrepos.api.ApiClient
import com.allen.gitrepos.api.ApiInterface
import com.allen.gitrepos.model.Repo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class HomeFragment : Fragment() {

    val compositeDisposable = CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        showPublicRepos()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun showPublicRepos() {

        val publicRepos = getGitPublicRespos()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())

        val disposableObserver = publicRepos.subscribeWith(object : DisposableObserver<List<Repo>>(){
            override fun onComplete() {
            }

            override fun onNext(repoItems: List<Repo>) {
                val listSize = repoItems.size
            }

            override fun onError(e: Throwable) {
            }

        })

        compositeDisposable.addAll(disposableObserver)
    }

    private fun getGitPublicRespos(): Observable<List<Repo>> {
        val retrofit =ApiClient.getClient()
        val apiInterface = retrofit.create(ApiInterface::class.java)
        return apiInterface.getRepo()
    }

}