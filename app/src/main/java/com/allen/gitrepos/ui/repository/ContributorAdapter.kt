package com.allen.gitrepos.ui.repository

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.allen.gitrepos.AppExectors
import com.allen.gitrepos.R
import com.allen.gitrepos.databinding.ContributorItemBinding
import com.allen.gitrepos.model.Contributor
import com.allen.gitrepos.ui.DataBoundListAdapter

class ContributorAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExectors: AppExectors,
    private val callback: ((Contributor, ImageView) -> Unit)?
) : DataBoundListAdapter<Contributor, ContributorItemBinding>(
    appExectors = appExectors,
    diffCallback = object : DiffUtil.ItemCallback<Contributor>() {
        override fun areItemsTheSame(oldItem: Contributor, newItem: Contributor): Boolean {
            return oldItem.login == newItem.login
        }

        override fun areContentsTheSame(oldItem: Contributor, newItem: Contributor): Boolean {
            return oldItem.avatarUrl == newItem.avatarUrl && oldItem.contributions == newItem.contributions
        }
    }
) {
    override fun createBinding(parent: ViewGroup): ContributorItemBinding {
        val binding = DataBindingUtil
            .inflate<ContributorItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.contributor_item,
                parent,
                false,
                dataBindingComponent
            )
        binding.root.setOnClickListener {
            binding.contributor?.let {
                callback?.invoke(it, binding.imageView)

            }
        }
        return binding
    }

    override fun bind(binding: ContributorItemBinding, item: Contributor) {
        binding.contributor = item
    }
}

