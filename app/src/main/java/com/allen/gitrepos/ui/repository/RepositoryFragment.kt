package com.allen.gitrepos.ui.repository

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.allen.gitrepos.AppExectors
import com.allen.gitrepos.R
import com.allen.gitrepos.binding.FragmentDataBindingComponent
import com.allen.gitrepos.databinding.FragmentRepositoryBinding
import com.allen.gitrepos.di.Injectable
import com.allen.gitrepos.ui.RetryCallback
import com.allen.gitrepos.utils.autoCleared
import javax.inject.Inject

class RepositoryFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val repoViewModel: RepositoryViewModel by viewModels {
        viewModelFactory
    }

    @Inject
    lateinit var appExectors: AppExectors

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<FragmentRepositoryBinding>()

    private val params by navArgs<RepositoryFragmentArgs>()

    private var adapter by autoCleared<ContributorAdapter>()

    private fun initContributorList(viewModel: RepositoryViewModel) {
        viewModel.contributors.observe(viewLifecycleOwner, Observer { listResource ->
            //we don't need any null check here for the adapter since LiveData guarantees that
            //it won't call us if fragment is stopped or not started.
            if (listResource?.data != null) {
                adapter.submitList(listResource.data)
            } else {
                adapter.submitList(emptyList())
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val dataBinding = DataBindingUtil.inflate<FragmentRepositoryBinding>(
            inflater, R.layout.fragment_repository, container, false
        )
        dataBinding.retryCallback = object : RetryCallback {
            override fun retry() {
                repoViewModel.retry()
            }
        }
        binding = dataBinding
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val params = RepositoryFragmentArgs.fromBundle(arguments!!)
        repoViewModel.setId(params.owner, params.name)
        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.repo = repoViewModel.repo

        val adapter =
            ContributorAdapter(dataBindingComponent, appExectors) { contributor, imageView ->
                val extras = FragmentNavigatorExtras(
                    imageView to contributor.login
                )
                findNavController().navigate(
                    RepositoryFragmentDirections.showUser(contributor.login, contributor.avatarUrl),
                    extras
                )

            }
        this.adapter = adapter
        binding.contributorList.adapter = adapter
        binding.contributorList.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }
        initContributorList(repoViewModel)
    }
}