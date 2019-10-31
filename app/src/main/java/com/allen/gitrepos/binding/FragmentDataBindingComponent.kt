package com.allen.gitrepos.binding

import androidx.databinding.DataBindingComponent
import androidx.fragment.app.Fragment

class FragmentDataBindingComponent(fragment: Fragment) : DataBindingComponent {

    private val adapters = FragmentBindingAdapters(fragment)

    override fun getFragmentBindingAdapters() = adapters
}